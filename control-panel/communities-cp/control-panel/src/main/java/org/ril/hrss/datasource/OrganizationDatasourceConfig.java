package org.ril.hrss.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.ril.hrss.repository"}, entityManagerFactoryRef = "orgDSEmFactory", transactionManagerRef = "orgDSTransactionManager")
public class OrganizationDatasourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource1")
    public DataSourceProperties orgDSProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource orgDS(@Qualifier("orgDSProperties") DataSourceProperties orgDSProperties) {
        return orgDSProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean orgDSEmFactory(@Qualifier("orgDS") DataSource orgrDS, EntityManagerFactoryBuilder builder) {
     /*   LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("persistence.org");
        em.setDataSource(orgrDS);
        em.setPackagesToScan("com.ril.svc.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.afterPropertiesSet();
        return em.getObject();*/
        return builder.dataSource(orgrDS).packages("org.ril.hrss.model").build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager orgDSTransactionManager(@Qualifier("orgDSEmFactory") EntityManagerFactory orgDSEmFactory) {
        return new JpaTransactionManager(orgDSEmFactory);
    }

}