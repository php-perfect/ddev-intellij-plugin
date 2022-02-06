package de.php_perfect.intellij.ddev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.util.concurrency.AppExecutorUtil;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.DdevCmdException;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.config.DdevConfigurationProvider;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;

// TODO: 06.02.2022 Tidy up after testing
public class PostStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DdevConfigService ddevConfigService = project.getService(DdevConfigService.class);
        ddevConfigService.watchChanges();

        if (!isDdevInstalled(project)) {
            System.out.println("ddev is not installed skipping");
            return;
        }

        try {
            System.out.println("Loading Status...");
            Description description = Ddev.getInstance(project).describe();
            System.out.println(description.getStatus());
        } catch (DdevCmdException e) {
            e.printStackTrace();
        }

        DdevNotifier.getInstance(project).notifyConfigChanged();
        DdevConfigurationProvider ddevConfigurationProvider = DdevConfigurationProvider.getInstance(project);
        ScheduledExecutorService scheduledExecutorService = AppExecutorUtil.getAppScheduledExecutorService();

        // scheduledExecutorService.scheduleWithFixedDelay(() -> {
        //try {
        //System.out.println("checking status...");
        // Status configuration = ddevConfigurationProvider.getDdevConfig();
        // System.out.println(configuration.getState());
        // } catch (DdevConfigurationException e) {
        //   e.printStackTrace();
        // }
        // }, 0L, 10L, TimeUnit.SECONDS);
    }

    private boolean isDdevInstalled(@NotNull Project project) {
        GeneralCommandLine commandLine = new GeneralCommandLine("ddev", "version")
                .withParameters("-j")
                .withWorkDirectory(project.getBasePath());

        try {
            commandLine.createProcess();
            return true;
        } catch (ExecutionException ignored) {
            return false;
        }
    }
}
