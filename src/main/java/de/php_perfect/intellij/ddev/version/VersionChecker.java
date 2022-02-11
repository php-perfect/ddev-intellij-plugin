package de.php_perfect.intellij.ddev.version;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Versions;
import de.php_perfect.intellij.ddev.config.DdevConfigurationProvider;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Service(Service.Level.PROJECT)
public final class VersionChecker {

    private final @NotNull Project project;

    public VersionChecker(@NotNull Project project) {
        this.project = project;
    }

    public void checkDdevVersion() {
        this.checkDdevVersion(false);
    }

    public void checkDdevVersion(boolean confirmNewestVersion) {
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Checking DDEV version", true) {

            public void run(@NotNull ProgressIndicator progressIndicator) {
                Versions versions = DdevConfigurationProvider.getInstance(project).getVersions();

                if (versions == null) {
                    // @todo Suggestion to install?
                    return;
                }

                progressIndicator.checkCanceled();

                try {
                    final LatestRelease latestRelease = GithubClient.getInstance().loadCurrentVersion(progressIndicator);
                    final ComparableVersion currentVersion = new ComparableVersion(versions.getDdevVersion());
                    final ComparableVersion latestVersion = new ComparableVersion(latestRelease.getTagName());

                    progressIndicator.checkCanceled();

                    if (latestVersion.compareTo(currentVersion) > 0) {
                        DdevNotifier.getInstance(project).notifyNewVersionAvailable(currentVersion.toString(), latestVersion.toString());
                    } else if (confirmNewestVersion) {
                        DdevNotifier.getInstance(project).notifyAlreadyLatestVersion();
                    }
                } catch (IOException ignored) {
                }
            }
        });
    }

    public static VersionChecker getInstance(@NotNull Project project) {
        return project.getService(VersionChecker.class);
    }
}
