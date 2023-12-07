package de.php_perfect.intellij.ddev.index;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagedConfigurationIndexTest extends BasePlatformTestCase {
    private static class SomeConfiguration implements IndexableConfiguration {
        @Override
        public int hashCode() {
            return 1;
        }
    }

    private static class SomeOtherConfiguration implements IndexableConfiguration {
        @Override
        public int hashCode() {
            return 2;
        }
    }

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    void addToIndexCheckId() {
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());
        managedConfigurationIndex.set("as73asvb324", SomeConfiguration.class, 123);

        assertTrue(managedConfigurationIndex.isManaged("as73asvb324", SomeConfiguration.class));
        assertFalse(managedConfigurationIndex.isManaged("fooBar", SomeConfiguration.class));
    }

    @Test
    void getFromIndex() {
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());
        managedConfigurationIndex.set("as73asvb324", SomeConfiguration.class, 123);

        assertSame("as73asvb324", managedConfigurationIndex.get(SomeConfiguration.class).id());
        assertSame(null, managedConfigurationIndex.get(SomeOtherConfiguration.class));
    }

    @Test
    void addToIndexCheckType() {
        final String key = "as73asvb324";
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());
        managedConfigurationIndex.set(key, SomeConfiguration.class, 123);

        assertTrue(managedConfigurationIndex.isManaged(key, SomeConfiguration.class));
        assertFalse(managedConfigurationIndex.isManaged(key, SomeOtherConfiguration.class));
    }

    @Test
    void removeIndex() {
        final String key = "as73asvb324";
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());

        managedConfigurationIndex.set(key, SomeConfiguration.class, 123);
        assertTrue(managedConfigurationIndex.isManaged(key, SomeConfiguration.class));

        managedConfigurationIndex.remove(SomeConfiguration.class);
        assertFalse(managedConfigurationIndex.isManaged(key, SomeConfiguration.class));
    }

    @Test
    void purgeIndex() {
        final String key = "as73asvb324";
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());

        managedConfigurationIndex.set(key, SomeConfiguration.class, 123);
        assertTrue(managedConfigurationIndex.isManaged(key, SomeConfiguration.class));

        managedConfigurationIndex.purge();
        assertFalse(managedConfigurationIndex.isManaged(key, SomeConfiguration.class));
    }

    @Test
    void isUpToDate() {
        final String key = "as73asvb324";
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());

        managedConfigurationIndex.set(key, SomeConfiguration.class, 123);
        assertTrue(managedConfigurationIndex.isUpToDate(SomeConfiguration.class, 123));
        assertFalse(managedConfigurationIndex.isUpToDate(SomeConfiguration.class, 321));
    }

    @Test
    void isUpToDateNonExistent() {
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.getProject());

        assertFalse(managedConfigurationIndex.isUpToDate(SomeConfiguration.class, 123));
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        ManagedConfigurationIndex.getInstance(this.getProject()).purge();

        super.tearDown();
    }
}
