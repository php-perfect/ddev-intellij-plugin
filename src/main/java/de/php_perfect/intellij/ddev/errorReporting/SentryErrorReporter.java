package de.php_perfect.intellij.ddev.errorReporting;

import com.intellij.diagnostic.AbstractMessage;
import com.intellij.diagnostic.IdeaReportingEvent;
import com.intellij.ide.DataManager;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.util.Consumer;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.cmd.Versions;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.protocol.SentryId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SentryErrorReporter extends ErrorReportSubmitter {
    @NotNull
    @Override
    public String getReportActionText() {
        return DdevIntegrationBundle.message("errorReporting.submit");
    }

    @Override
    public boolean submit(IdeaLoggingEvent @NotNull [] events, @Nullable String additionalInfo, @NotNull Component parentComponent, @NotNull Consumer<? super SubmittedReportInfo> consumer) {
        DataContext context = DataManager.getInstance().getDataContext(parentComponent);
        Project project = CommonDataKeys.PROJECT.getData(context);

        ProgressManager.getInstance().run(new Task.Backgroundable(project, DdevIntegrationBundle.message("errorReporting.taskTitle")) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setIndeterminate(true);

                List<String> sentryIds = new ArrayList<>();

                AtomicBoolean oneOrMoreFailed = new AtomicBoolean(false);

                for (IdeaLoggingEvent event : events) {
                    SentryId sentryId = captureIdeaLoggingEvent(event, oneOrMoreFailed);
                    sentryIds.add(sentryId.toString());
                }

                if (project != null) {
                    DdevNotifier.getInstance(project).asyncNotifyErrorReportSent(sentryIds);
                }

                var submissionStatus = oneOrMoreFailed.get() ?
                        SubmittedReportInfo.SubmissionStatus.FAILED :
                        SubmittedReportInfo.SubmissionStatus.NEW_ISSUE;
                consumer.consume(new SubmittedReportInfo(submissionStatus));
            }
        });

        return true;
    }

    private SentryId captureIdeaLoggingEvent(IdeaLoggingEvent event, AtomicBoolean oneOrMoreFailed) {
        ThreadSync threadSync = new ThreadSync();

        SentryId sentryId = Sentry.captureEvent(buildSentryEvent(event), new SentrySubmissionResult((success) -> {
            if (!success) {
                oneOrMoreFailed.set(true);
            }

            threadSync.release();
        }));

        threadSync.waitForRelease();

        return sentryId;
    }

    private SentryEvent buildSentryEvent(IdeaLoggingEvent ideaLoggingEvent) {
        SentryEvent event = new SentryEvent();
        event.setRelease(getPluginDescriptor().getVersion());

        event.setThrowable(ideaLoggingEvent.getThrowable());
        if (ideaLoggingEvent instanceof IdeaReportingEvent && ideaLoggingEvent.getData() instanceof AbstractMessage) {
            event.setThrowable(((AbstractMessage) ideaLoggingEvent.getData()).getThrowable());
        }

        event.setExtra("last_action_id", IdeaLogger.ourLastActionId);

        return event;
    }
}
