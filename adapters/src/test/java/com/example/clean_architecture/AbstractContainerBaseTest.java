package com.example.clean_architecture;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractContainerBaseTest {

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest");
        POSTGRESQL_CONTAINER.start();
    }
}
