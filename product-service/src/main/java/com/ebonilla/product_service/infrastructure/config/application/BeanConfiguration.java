package com.ebonilla.product_service.infrastructure.config.application;

import com.ebonilla.product_service.application.ports.output.*;
import com.ebonilla.product_service.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /* Este archivo de configuracion es para crear los casos de uso como Beans para que el contexto de
    *  spring lo reconozca y nos permita la inyeccion del repositorio, se puede con este archivo o podemos
    *  agregarle la anotacion @Service a los casos de uso. Crear este archivo deja la capa de aplicacion mas
    *  limpia de dependencias de Spring.
    */

    @Bean
    public CategoryUseCases categoryUseCases(ICategoryPort categoryPort){
        return new CategoryUseCases(categoryPort);
    }

    @Bean
    public MeasurementUseCases measurementUseCases(IMeasurementPort measurementPort) {
        return new MeasurementUseCases(measurementPort);
    }

    @Bean
    public ProductUseCases productUseCases(IProductPort productPort, ICategoryPort categoryPort, IMeasurementPort measurementPort){
        return new ProductUseCases(productPort, categoryPort, measurementPort);
    }

    @Bean
    public SupplierUseCases supplierUseCases(ISupplierPort supplierPort){
        return new SupplierUseCases(supplierPort);
    }

    @Bean
    public ProductSupplierUseCases productSupplierUseCases(IProductSupplierPort productSupplierPort, IProductPort productPort,
                                                           ISupplierPort supplierPort, IPSRelationPort relationPort){
        return new ProductSupplierUseCases(productSupplierPort, productPort, supplierPort, relationPort);
    }

    @Bean
    public CreateRelationUseCase createRelationUseCase(ICategoryPort categoryPort, IMeasurementPort measurementPort,
                                                       IProductPort productPort, ISupplierPort supplierPort, IProductSupplierPort productSupplierPort){
        return new CreateRelationUseCase(categoryPort, measurementPort, productPort, supplierPort, productSupplierPort);
    }
}
