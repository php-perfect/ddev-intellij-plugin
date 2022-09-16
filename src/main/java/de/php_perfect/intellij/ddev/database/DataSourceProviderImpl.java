package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.*;
import com.intellij.database.introspection.DBIntrospectionOptions;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.model.ObjectName;
import com.intellij.database.util.TreePattern;
import com.intellij.database.util.TreePatternUtils;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DataSourceProviderImpl implements DataSourceProvider {

    public static final String DSN_HOST = "127.0.0.1";

    public @Nullable LocalDataSource buildDdevDataSource(@NotNull DatabaseInfo databaseInfo) {
        final DatabaseInfo.Type databaseType = databaseInfo.getType();
        if (databaseType == null) {
            return null;
        }

        final DatabaseDriver driver = getDriverByDatabaseType(databaseType);
        final String dsn = this.getDsnByDatabaseType(databaseInfo);

        return buildDataSource(driver, dsn);
    }

    private @NotNull LocalDataSource buildDataSource(DatabaseDriver driver, String dsn) {
        final LocalDataSource dataSource = buildDataSourceFromDriverAndUrl(driver, dsn);
        dataSource.setAutoSynchronize(true);
        dataSource.setCheckOutdated(true);
        dataSource.setSourceLoading(DBIntrospectionOptions.SourceLoading.USER_AND_SYSTEM_SOURCES);

        final TreePattern treePattern = new TreePattern(TreePatternUtils.create(ObjectName.NULL, ObjectKind.SCHEMA));
        dataSource.setIntrospectionScope(treePattern);
        dataSource.getSchemaMapping().setIntrospectionScope(treePattern);

        final DataSourceSchemaMapping dataSourceSchemaMapping = new DataSourceSchemaMapping();
        dataSource.setIntrospectionScope(treePattern);
        dataSource.setSchemaMapping(dataSourceSchemaMapping);

        return dataSource;
    }

    private @NotNull LocalDataSource buildDataSourceFromDriverAndUrl(DatabaseDriver driver, String connectionUrl) {
        LocalDataSource dataSource = LocalDataSource.fromDriver(driver, connectionUrl, false);
        dataSource.setName("DDEV");
        dataSource.setComment("DDEV generated data source");
        dataSource.setUsername("db");
        dataSource.setConfiguredByUrl(true);
        dataSource.setSchemaControl(SchemaControl.AUTOMATIC);

        return dataSource;
    }

    private @NotNull DatabaseDriver getDriverByDatabaseType(@NotNull DatabaseInfo.Type databaseType) {
        DatabaseDriverManager databaseDriverManager = DatabaseDriverManager.getInstance();

        switch (databaseType) {
            case POSTGRESQL:
                return databaseDriverManager.getDriver("postgresql");
            case MARIADB:
                return databaseDriverManager.getDriver("mariadb");
            case MYSQL:
            default:
                return databaseDriverManager.getDriver("mysql.8");
        }
    }

    private @NotNull String getDsnByDatabaseType(@NotNull DatabaseInfo databaseInfo) {
        return String.format(
                "jdbc:%s://%s:%d/%s?user=%s&password=%s",
                getDsnType(databaseInfo.getType()),
                DSN_HOST,
                databaseInfo.getPublishedPort(),
                databaseInfo.getName(),
                databaseInfo.getUsername(),
                databaseInfo.getPassword()
        );
    }

    private static @NotNull String getDsnType(@Nullable DatabaseInfo.Type databaseType) {
        if (databaseType == null) {
            return "mysql";
        }

        switch (databaseType) {
            case POSTGRESQL:
                return "postgresql";
            case MARIADB:
                return "mariadb";
            default:
                return "mysql";
        }
    }
}
