package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DataSourceProviderTest extends BasePlatformTestCase {
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
    void mySql() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("DDEV", "Some Description", DataSourceConfig.Type.MYSQL, "8.0", "127.0.0.1", 12345, "db", "some-user", "some-password");

        final LocalDataSource dataSource = new LocalDataSource();
        new DataSourceProviderImpl().updateDataSource(dataSource, dataSourceConfig);

        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(dataSource.getDatabaseDriver());
        Assertions.assertEquals("mysql.8", dataSource.getDatabaseDriver().getId());
        Assertions.assertEquals("jdbc:mysql://127.0.0.1:12345/db?user=some-user&password=some-password", dataSource.getUrl());
    }

    @Test
    void mySql56() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("DDEV", "Some Description", DataSourceConfig.Type.MYSQL, "5.6", "127.0.0.1", 12345, "db", "some-user", "some-password");

        final LocalDataSource dataSource = new LocalDataSource();
        new DataSourceProviderImpl().updateDataSource(dataSource, dataSourceConfig);

        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(dataSource.getDatabaseDriver());
        Assertions.assertEquals("mysql", dataSource.getDatabaseDriver().getId());
        Assertions.assertEquals("jdbc:mysql://127.0.0.1:12345/db?user=some-user&password=some-password", dataSource.getUrl());
    }

    @Test
    void mariaDb() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("DDEV", "Some Description", DataSourceConfig.Type.MARIADB, "10.4", "127.0.0.1", 12345, "db", "some-user", "some-password");

        final LocalDataSource dataSource = new LocalDataSource();
        new DataSourceProviderImpl().updateDataSource(dataSource, dataSourceConfig);

        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertEquals("jdbc:mariadb://127.0.0.1:12345/db?user=some-user&password=some-password", dataSource.getUrl());
    }

    @Test
    void postgreSql() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("DDEV", "Some Description", DataSourceConfig.Type.POSTGRESQL, "15.5", "127.0.0.1", 12345, "db", "some-user", "some-password");

        final LocalDataSource dataSource = new LocalDataSource();
        new DataSourceProviderImpl().updateDataSource(dataSource, dataSourceConfig);

        Assertions.assertInstanceOf(LocalDataSource.class, dataSource);
        Assertions.assertNotNull(dataSource);
        Assertions.assertEquals("jdbc:postgresql://127.0.0.1:12345/db?user=some-user&password=some-password", dataSource.getUrl());
    }
}
