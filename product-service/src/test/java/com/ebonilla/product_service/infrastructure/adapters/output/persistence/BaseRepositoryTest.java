package com.ebonilla.product_service.infrastructure.adapters.output.persistence;

import com.ebonilla.product_service.infrastructure.adapters.config.IntegrationTestConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(IntegrationTestConfiguration.class)
public abstract class BaseRepositoryTest {

}
