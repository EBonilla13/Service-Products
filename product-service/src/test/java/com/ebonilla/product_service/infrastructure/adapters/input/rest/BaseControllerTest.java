package com.ebonilla.product_service.infrastructure.adapters.input.rest;

import com.ebonilla.product_service.infrastructure.adapters.config.IntegrationTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Import(IntegrationTestConfiguration.class)
public abstract class BaseControllerTest {

    // key sonar sqp_d3dde0b97f4d8f8d6fa2fbd4fd7a566adbd03842

}
