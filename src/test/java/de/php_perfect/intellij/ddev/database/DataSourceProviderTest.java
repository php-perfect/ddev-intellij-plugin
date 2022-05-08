package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DataSourceProviderTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testMySQL() {
        DatabaseInfo databaseInfo = new DatabaseInfo(DatabaseInfo.Type.MYSQL, "8.0", 533, "", "some-internal-host", "some-user", "some-password", 12345);

        DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();

        LocalDataSource dataSource = dataSourceProvider.buildDdevDataSource(databaseInfo);
        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertEquals("jdbc:mysql://127.0.0.1:12345/?user=some-user&password=some-password", dataSource.getUrl());
    }

    @Test
    public void testPostgreSQL() {
        DatabaseInfo databaseInfo = new DatabaseInfo(DatabaseInfo.Type.POSTGRESQL, "8.0", 533, "", "some-internal-host", "some-user", "some-password", 12345);

        DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();

        LocalDataSource dataSource = dataSourceProvider.buildDdevDataSource(databaseInfo);
        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertEquals("jdbc:postgresql://127.0.0.1:12345/?user=some-user&password=some-password", dataSource.getUrl());
    }

}
