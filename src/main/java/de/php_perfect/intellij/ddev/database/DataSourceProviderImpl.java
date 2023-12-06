package de.php_perfect.intellij.ddev.database;

import com.intellij.database.Dbms;
import com.intellij.database.dataSource.DatabaseDriver;
import com.intellij.database.dataSource.DatabaseDriverManager;
import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.database.dataSource.SchemaControl;
import com.intellij.database.introspection.DBIntrospectionOptions;
import com.intellij.database.introspection.DBIntrospectorFeatures;
import com.intellij.database.model.properties.Level;
import com.intellij.database.util.TreePattern;
import com.intellij.database.util.TreePatternUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.NotNull;

import static com.intellij.database.introspection.DBIntrospectionConsts.ALL_NAMESPACES;

public final class DataSourceProviderImpl implements DataSourceProvider {
    public void updateDataSource(final @NotNull LocalDataSource dataSource, final @NotNull DataSourceConfig dataSourceConfig) {
        final DatabaseDriver driver = this.getDriverByDatabaseType(dataSourceConfig.type(), dataSourceConfig.version());
        final String dsn = getDsnByDatabaseType(dataSourceConfig);

        dataSource.setUrlSmart(dsn);
        dataSource.setDatabaseDriver(driver);
        dataSource.setPasswordStorage(LocalDataSource.Storage.PERSIST);
        dataSource.setName(dataSourceConfig.name());
        dataSource.setComment(dataSourceConfig.description());
        dataSource.setUsername(dataSourceConfig.username());
        dataSource.setSchemaControl(SchemaControl.AUTOMATIC);
        dataSource.setAutoSynchronize(true);
        dataSource.setCheckOutdated(true);
        dataSource.setSourceLoading(DBIntrospectionOptions.SourceLoading.USER_AND_SYSTEM_SOURCES);

        final Dbms dbms = Dbms.forConnection(dataSource);
        final TreePattern treePattern = TreePatternUtils.importPattern(dbms, ALL_NAMESPACES);
        dataSource.setIntrospectionScope(treePattern);
        dataSource.setIntrospectionLevel(DBIntrospectorFeatures.supportsMultilevelIntrospection(dbms) ? Level.L3 : null);
    }

    private static @NotNull String getDsnByDatabaseType(final @NotNull DataSourceConfig databaseInfo) {
        return String.format(
                "jdbc:%s://%s:%d/%s?user=%s&password=%s",
                getDsnType(databaseInfo.type()),
                databaseInfo.host(),
                databaseInfo.port(),
                databaseInfo.database(),
                databaseInfo.username(),
                databaseInfo.password()
        );
    }

    private @NotNull DatabaseDriver getDriverByDatabaseType(final @NotNull DataSourceConfig.Type databaseType, final @NotNull String version) {
        final DatabaseDriverManager databaseDriverManager = DatabaseDriverManager.getInstance();

        return switch (databaseType) {
            case POSTGRESQL -> databaseDriverManager.getDriver("postgresql");
            case MARIADB -> databaseDriverManager.getDriver("mariadb");
            case MYSQL ->
                    new ComparableVersion(version).compareTo(new ComparableVersion("8.0")) < 0 ? databaseDriverManager.getDriver("mysql") : databaseDriverManager.getDriver("mysql.8");
        };
    }

    private static @NotNull String getDsnType(final @NotNull DataSourceConfig.Type databaseType) {
        return switch (databaseType) {
            case POSTGRESQL -> "postgresql";
            case MARIADB -> "mariadb";
            case MYSQL -> "mysql";
        };
    }
}
