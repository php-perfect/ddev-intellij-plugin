package de.php_perfect.intellij.ddev.version;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class VersionCheckerImplTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void checkDdevVersion() {
        VersionCheckerImpl versionChecker = new VersionCheckerImpl(this.getProject());
        versionChecker.checkDdevVersion();
    }

    @Test
    void checkDdevVersionWithConfirmation() {
        VersionCheckerImpl versionChecker = new VersionCheckerImpl(this.getProject());
        versionChecker.checkDdevVersion(true);
    }
}
