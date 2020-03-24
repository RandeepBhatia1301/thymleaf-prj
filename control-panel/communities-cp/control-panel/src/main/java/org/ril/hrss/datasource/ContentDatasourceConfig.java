package org.ril.hrss.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.ril.hrss.repository"}, entityManagerFactoryRef = "contentDSEmFactory", transactionManagerRef = "contentDSTransactionManager")
public class ContentDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource2")
    public DataSourceProperties contentDSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource contentDS(@Qualifier("contentDSProperties") DataSourceProperties contentDSProperties) {
        return contentDSProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean contentDSEmFactory(@Qualifier("contentDS") DataSource contentDS, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(contentDS).packages("org.ril.hrss.model").build();
    }

    @Bean
    public PlatformTransactionManager contentDSTransactionManager(@Qualifier("contentDSEmFactory") EntityManagerFactory contentDSEmFactory) {
        return new JpaTransactionManager(contentDSEmFactory);
    }
}

