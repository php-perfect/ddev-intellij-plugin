package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.jetbrains.php.config.interpreters.PhpInterpretersPhpInfoCacheImpl;
import com.jetbrains.php.config.interpreters.PhpSdkAdditionalData;
import com.jetbrains.php.config.phpInfo.PhpInfo;
import com.jetbrains.php.config.phpInfo.PhpInfoUtil;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// @todo: Check Environment Variables
public final class DdevPhpInterpreterManagerImpl implements DdevPhpInterpreterManager {
    private final @NotNull Project project;

    public DdevPhpInterpreterManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void updateDdevPhpInterpreter(@NotNull PhpInterpreter phpInterpreter) {
        PhpInterpretersManagerImpl interpretersManager = PhpInterpretersManagerImpl.getInstance(this.project);

        PhpInterpreter existingInterpreter = interpretersManager.findInterpreter(phpInterpreter.getName());
        if (this.needsReplacement(existingInterpreter, phpInterpreter)) {
            List<PhpInterpreter> interpreters = interpretersManager.getInterpreters();
            interpreters.remove(existingInterpreter);
            interpreters.add(phpInterpreter);
            interpretersManager.setInterpreters(interpreters);
            PhpInfo phpInfo = PhpInfoUtil.getPhpInfo(this.project, phpInterpreter, null);
            PhpInterpretersPhpInfoCacheImpl.getInstance(this.project).setPhpInfo(phpInterpreter.getName(), phpInfo);
        }
    }

    private boolean needsReplacement(@Nullable PhpInterpreter current, @NotNull PhpInterpreter replacement) {
        if (current == null) {
            return true;
        }

        PhpSdkAdditionalData currentSdk = current.getPhpSdkAdditionalData();

        if (!(currentSdk instanceof PhpRemoteSdkAdditionalData)) {
            return true;
        }

        PhpRemoteSdkAdditionalData replacementSdk = (PhpRemoteSdkAdditionalData) replacement.getPhpSdkAdditionalData();

        return !((PhpRemoteSdkAdditionalData) currentSdk).getInterpreterPath().equals(replacementSdk.getInterpreterPath());
    }
}
