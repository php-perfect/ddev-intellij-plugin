package de.php_perfect.intellij.ddev.php.composer;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import com.jetbrains.php.composer.execution.ComposerExecution;
import com.jetbrains.php.composer.execution.ComposerExecutionProvider;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Configuration form for DDEV Composer execution.
 */
public class DdevComposerForm implements ComposerExecutionProvider.Form {
    @NotNull
    private final Project myProject;
    private JPanel myMainPanel;
    private JBLabel myDescriptionLabel;

    public DdevComposerForm(@NotNull Project project, @NotNull Disposable disposable) {
        myProject = project;
        createUIComponents();
    }

    private void createUIComponents() {
        myDescriptionLabel = new JBLabel(DdevIntegrationBundle.message("composer.form.ddev.description"));
        myDescriptionLabel.setFont(myDescriptionLabel.getFont().deriveFont(Font.ITALIC));

        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(myDescriptionLabel)
                .getPanel();
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return myMainPanel;
    }

    @Nullable
    @Override
    public ValidationInfo validate() {
        // No validation needed for DDEV composer
        return null;
    }

    @Override
    public boolean reset(@NotNull ComposerExecution execution) {
        // No configuration to reset for DDEV composer
        return execution instanceof DdevComposerExecution;
    }

    @Override
    public void apply() {
        // No additional configuration needed for DDEV
    }

    @NotNull
    public ComposerExecution createExecution() {
        return new DdevComposerExecution();
    }

    @NotNull
    @Override
    public ComposerExecution getExecution() {
        return createExecution();
    }

    @Override
    public boolean isModified(@NotNull ComposerExecution execution) {
        // No configuration to modify for DDEV composer
        return false;
    }
}
