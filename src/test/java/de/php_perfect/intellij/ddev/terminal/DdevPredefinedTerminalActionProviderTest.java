package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DdevPredefinedTerminalActionProviderTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void listOpenPredefinedTerminalActions() {
        Project project = getProject();
        DdevPredefinedTerminalActionProvider ddevPredefinedTerminalActionProvider = new DdevPredefinedTerminalActionProvider();

        Assertions.assertTrue(ddevPredefinedTerminalActionProvider.listOpenPredefinedTerminalActions(project).isEmpty());
    }
}
