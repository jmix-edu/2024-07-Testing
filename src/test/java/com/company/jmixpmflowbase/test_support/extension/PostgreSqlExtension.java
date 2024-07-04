package com.company.jmixpmflowbase.test_support.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSqlExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer postgreSQLContainer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        postgreSQLContainer = new PostgreSQLContainer<>(
                DockerImageName.parse("postgres")
                        .withTag("15.1"))
                .withDatabaseName("postgres-test-db")
                .withUsername("test")
                .withPassword("pass");
        postgreSQLContainer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        postgreSQLContainer.stop();
    }
}
