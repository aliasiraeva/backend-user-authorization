package ru.itis.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.util.Properties;


@AllArgsConstructor
public class DataSourceConfiguration {

    private Properties properties;

    public DataSource customDataSource() {
        CustomDataSource customDataSource = new CustomDataSource();

        customDataSource.setUrl(properties.getProperty("database.url"));
        customDataSource.setUsername(properties.getProperty("database.username"));
        customDataSource.setPassword(properties.getProperty("database.password"));

        return customDataSource;
    }

    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(properties.getProperty("database.url"));
        hikariConfig.setUsername(properties.getProperty("database.username"));
        hikariConfig.setPassword(properties.getProperty("database.password"));
        hikariConfig.setDriverClassName(properties.getProperty("database.driver-name"));
        hikariConfig.setMaximumPoolSize(
                Integer.parseInt(properties.getProperty("database.hikari.max-pool-size")));

        return new HikariDataSource(hikariConfig);
    }

}
