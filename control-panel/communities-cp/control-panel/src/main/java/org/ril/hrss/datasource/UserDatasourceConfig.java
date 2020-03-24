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
@EnableJpaRepositories(basePackages = {"org.ril.hrss.repository"}, entityManagerFactoryRef = "userDSEmFactory", transactionManagerRef = "userDSTransactionManager")
public class UserDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource3")
    public DataSourceProperties userDSProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource userDS(@Qualifier("userDSProperties") DataSourceProperties userDSProperties) {
        return userDSProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userDSEmFactory(@Qualifier("userDS") DataSource userDS, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(userDS).packages("org.ril.hrss.model").build();
    }

    @Bean
    public PlatformTransactionManager userDSTransactionManager(@Qualifier("userDSEmFactory") EntityManagerFactory userDSEmFactory) {
        return new JpaTransactionManager(userDSEmFactory);
    }
}
