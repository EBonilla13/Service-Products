package com.ebonilla.product_service.infrastructure.adapters.config;

import org.testcontainers.postgresql.PostgreSQLContainer;

public final class PostgreSqlTestContainer {

    private PostgreSqlTestContainer(){}

    private static class Holder {

        private static final PostgreSQLContainer INSTANCE =
                new PostgreSQLContainer("postgres:18-alpine")
                        .withDatabaseName("test")
                        .withUsername("postgres")
                        .withPassword("postgres")
                        .withInitScript("database/schema.sql")
                        .withReuse(false);

        static {
            INSTANCE.start();
        }
    }

    public static PostgreSQLContainer getInstance(){
        return Holder.INSTANCE;
    }
}
