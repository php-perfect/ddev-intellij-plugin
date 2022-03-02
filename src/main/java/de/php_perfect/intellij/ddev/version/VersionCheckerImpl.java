package de.php_perfect.intellij.ddev.version;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Versions;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Service(Service.Level.PROJECT)
public final class VersionCheckerImpl {
    private static final Logger LOG = Logger.getInstance(VersionCheckerImpl.class);

    private final @NotNull Project project;

    public VersionCheckerImpl(@NotNull Project project) {
        this.project = project;
    }

    public void checkDdevVersion() {
        this.checkDdevVersion(false);
    }

    public void checkDdevVersion(boolean confirmNewestVersion) {
        State state = DdevStateManager.getInstance(this.project).getState();

        if (!state.isInstalled()) {
            return;
        }

        Versions versions = state.getVersions();

        if (versions == null) {
            // @todo Suggestion to install?
            return;
        }

        String ddevVersion = versions.getDdevVersion();

        if (ddevVersion == null) {
            // @todo Suggestion to install?
            return;
        }

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Checking DDEV version", true) {
            public void run(@NotNull ProgressIndicator progressIndicator) {
                LatestRelease latestRelease = loadLatestRelease(progressIndicator);
                progressIndicator.checkCanceled();

                if (latestRelease == null || latestRelease.getTagName() == null) {
                    return;
                }

                final ComparableVersion currentVersion = new ComparableVersion(ddevVersion);
                final ComparableVersion latestVersion = new ComparableVersion(latestRelease.getTagName());

                if (latestVersion.compareTo(currentVersion) < 0) {
                    DdevNotifier.getInstance(project).asyncNotifyNewVersionAvailable(ddevVersion, latestVersion.toString());
                } else if (confirmNewestVersion) {
                    DdevNotifier.getInstance(project).asyncNotifyAlreadyLatestVersion();
                }
            }
        });
    }

    private LatestRelease loadLatestRelease(@NotNull ProgressIndicator progressIndicator) {
        try {
            return GithubClient.getInstance().loadCurrentVersion(progressIndicator);
        } catch (IOException e) {
            LOG.error(e);
            return null;
        }
    }

    public static VersionCheckerImpl getInstance(@NotNull Project project) {
        return project.getService(VersionCheckerImpl.class);
    }
}
