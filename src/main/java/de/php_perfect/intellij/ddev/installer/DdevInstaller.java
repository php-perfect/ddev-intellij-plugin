package de.php_perfect.intellij.ddev.installer;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.wsl.WslHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service(Service.Level.PROJECT)
public final class DdevInstaller {

    private final Project project;

    public DdevInstaller(Project project) {
        this.project = project;
    }

    public static DdevInstaller getInstance(@NotNull Project project) {
        return project.getService(DdevInstaller.class);
    }

    public void install() {
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Installing DDEV", true, PerformInBackgroundOption.DEAF) {
            private @Nullable Process process;

            public void run(@NotNull ProgressIndicator progressIndicator) {
                if (this.myProject == null) {
                    return;
                }

                progressIndicator.setIndeterminate(false);
                progressIndicator.setText2("Downloading DDEV installer");

                final File file = new File(this.myProject.getBasePath() + "/install_ddev.sh");

                try {
                    final URL url = new URL("https://raw.githubusercontent.com/drud/ddev/master/scripts/install_ddev.sh");
                    download(url, file, progressIndicator);
                    file.deleteOnExit();
                } catch (IOException ignored) {
                    return;
                }

                progressIndicator.setIndeterminate(true);
                progressIndicator.setText2("Running DDEV installer");

                try {
                    GeneralCommandLine cli = createCommandLine(file);
                    System.out.println(cli.getCommandLineString());
                    this.process = cli.createProcess();
                    logOutput(this.process);
                    this.process.waitFor();
                } catch (ExecutionException ignored) {
                    // @todo notify error
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                // @todo notify success
            }

            @Override
            public void onCancel() {
                super.onCancel();
                if (this.process != null) {
                    this.process.destroyForcibly();
                }
            }

            @NotNull
            private GeneralCommandLine createCommandLine(File file) {
                if (this.myProject == null) {
                    // @todo: Just a placeholder
                    throw new RuntimeException("Placeholder");
                }

                final @Nullable String distro = WslHelper.parseWslDistro(this.myProject.getBasePath());
                System.out.println(file.getPath());
                System.out.println(distro);

                if (distro == null) {
                    return new GeneralCommandLine("bash", file.getName())
                            .withWorkDirectory(file.getParent());
                }

                return new GeneralCommandLine("wsl", "-d", distro, "-u", "root", "bash", file.getName())
                        .withWorkDirectory(file.getParent());
            }
        });
    }

    private void download(URL url, File file, @NotNull ProgressIndicator progressIndicator) throws IOException {
        final URLConnection connection = url.openConnection();
        final long completeFileSize = connection.getContentLength();

        try (final FileOutputStream fos = new FileOutputStream(file);
             final BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
             final BufferedInputStream in = new BufferedInputStream(connection.getInputStream())
        ) {
            byte[] data = new byte[1024];
            long downloadedFileSize = 0;
            int x;
            while ((x = in.read(data, 0, 1024)) >= 0) {
                downloadedFileSize += x;
                progressIndicator.setFraction((double) downloadedFileSize / (double) completeFileSize);
                bout.write(data, 0, x);
            }
        }
    }

    private void logOutput(Process process) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        BufferedReader in2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = in2.readLine()) != null) {
            System.out.println(line);
        }
    }

    private void execute() throws CommandFailedException {
        final GeneralCommandLine commandLine = new GeneralCommandLine("curl", "-LO", "https://raw.githubusercontent.com/drud/ddev/master/scripts/install_ddev.sh", "&&", "bash install_ddev.sh");

        try {
            final Process process = commandLine.createProcess();
        } catch (ExecutionException exception) {
            throw new CommandFailedException("Command not found " + commandLine.getCommandLineString(), exception);
        }
    }
}
