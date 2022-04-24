package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.PtyCommandLine;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.php.PhpVersion;
import de.php_perfect.intellij.ddev.state.DdevConfigLoader;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class DdevRunnerImpl implements DdevRunner {

    @Override
    public void start(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.start");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("start", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void restart(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.restart");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("restart", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void stop(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.stop");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("stop", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void powerOff(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.powerOff");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("poweroff", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void delete(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.delete");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("delete", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void share(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.share");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.createCommandLine("share", project), title, () -> this.updateDescription(project));
    }

    @Override
    public void config(@NotNull Project project) {
        final String title = DdevIntegrationBundle.message("ddev.run.config");
        final Runner runner = Runner.getInstance(project);
        runner.run(this.buildConfigCommandLine(project), title, () -> {
                    DdevStateManager.getInstance(project).updateConfiguration();
                    this.updateDescription(project);
                    VirtualFile ddevConfig = DdevConfigLoader.getInstance(project).load();

                    if (ddevConfig != null && ddevConfig.exists()) {
                        FileEditorManager.getInstance(project).openFile(ddevConfig, true);
                    }
                }
        );
    }

    private void updateDescription(Project project) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> DdevStateManager.getInstance(project).updateDescription());
    }

    private @NotNull GeneralCommandLine buildConfigCommandLine(@NotNull Project project) {
        GeneralCommandLine commandLine = this.createCommandLine("config", project)
                .withParameters("--auto");

        String phpVersion = PhpVersion.getLanguageLevelIfAvailable(project);

        if (phpVersion != null) {
            commandLine.addParameters("--php-version", phpVersion);
        }

        return commandLine;
    }

    private @NotNull GeneralCommandLine createCommandLine(@NotNull String ddevAction, @NotNull Project project) {
        return new PtyCommandLine(List.of("ddev", ddevAction))
                .withInitialRows(30)
                .withInitialColumns(120)
                .withWorkDirectory(project.getBasePath())
                .withCharset(StandardCharsets.UTF_8)
                .withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}
