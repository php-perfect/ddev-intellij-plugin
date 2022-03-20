package de.php_perfect.intellij.ddev.version;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class VersionCheckerImplTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void checkDdevVersion() {
        VersionCheckerImpl versionChecker = new VersionCheckerImpl(this.getProject());
        versionChecker.checkDdevVersion();
    }

    @Test
    public void testCheckDdevVersion() {
        VersionCheckerImpl versionChecker = new VersionCheckerImpl(this.getProject());
        versionChecker.checkDdevVersion(true);
    }
}
