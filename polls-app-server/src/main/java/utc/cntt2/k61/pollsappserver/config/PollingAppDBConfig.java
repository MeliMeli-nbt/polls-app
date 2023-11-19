package utc.cntt2.k61.pollsappserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
@EnableJpaRepositories(
        basePackages = "utc.cntt2.k61.pollsappserver.repository",
        entityManagerFactoryRef = "pollsAppEntityManagerFactoryRef",
        transactionManagerRef = "pollsAppTransactionManagerRef")
public class PollingAppDBConfig {
    @Primary
    @Bean(name = "pollsAppDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.polling-app")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "pollsAppEntityManagerFactoryRef")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("pollsAppDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("utc.cntt2.k61.pollsappserver.domain")
                .persistenceUnit("pollsApp")
                .build();
    }

    @Primary
    @Bean(name = "pollsAppTransactionManagerRef")
    public PlatformTransactionManager transactionManager(
            @Qualifier("pollsAppEntityManagerFactoryRef") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
