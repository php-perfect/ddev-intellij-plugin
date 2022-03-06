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
        DatabaseDriverManager instance = DatabaseDriverManagerImpl.getInstance();

        switch (databaseType) {
            case POSTGRESQL:
                return instance.getDriver("postgresql");
            case MARIADB:
                return instance.getDriver("mariadb");
            case MYSQL:
            default:
                return instance.getDriver("mysql.8");
        }
    }

    private @NotNull String getDsnByDatabaseType(@NotNull DatabaseInfo databaseInfo) {
        String dsnType = "mysql";

        if (databaseInfo.getType() == DatabaseInfo.Type.POSTGRESQL) {
            dsnType = "postgresql";
        }

        return String.format("jdbc:%s://localhost:%d/%s?user=%s&password=%s", dsnType, databaseInfo.getPublishedPort(), databaseInfo.getName(), databaseInfo.getUsername(), databaseInfo.getPassword());
    }
}
