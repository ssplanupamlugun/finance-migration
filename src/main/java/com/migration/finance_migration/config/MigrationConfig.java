package com.migration.finance_migration.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "migration")
public class MigrationConfig {

    /**
     * Finance API Base URL.
     */
    private String baseUrl;

    private String bankAccountCreateUrl;


}