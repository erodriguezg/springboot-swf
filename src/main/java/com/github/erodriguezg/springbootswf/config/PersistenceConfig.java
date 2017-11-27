package com.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eduar on 14/05/2017.
 */
@Configuration
public class PersistenceConfig {

    private static final String QUALIFIER_JPA_PROPERTY_MAP = "jpaPropertyMap";

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("hikari") DataSource dataSource,
            @Qualifier(QUALIFIER_JPA_PROPERTY_MAP) Map<String, Object> jpaPropertiesMap) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("persistenceUnit");
        emf.setPackagesToScan("com.github.erodriguezg.springbootswf.entities");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(jpaPropertiesMap);
        return emf;
    }

    @Bean
    @Qualifier(QUALIFIER_JPA_PROPERTY_MAP)
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_DEVELOPMENT)
    public Map<String, Object> jpaPropertiesMapDevelopment() {
        Map<String, Object> jpaPropertiesMap = crearMapaJpaComun();
        jpaPropertiesMap.put("hibernate.show_sql", true);
        jpaPropertiesMap.put("hibernate.format_sql", true);
        return jpaPropertiesMap;
    }

    @Bean
    @Qualifier(QUALIFIER_JPA_PROPERTY_MAP)
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_PRODUCTION)
    public Map<String, Object> jpaPropertiesMapProduction() {
        Map<String, Object> jpaPropertiesMap = crearMapaJpaComun();
        jpaPropertiesMap.put("hibernate.show_sql", false);
        jpaPropertiesMap.put("hibernate.format_sql", false);
        return jpaPropertiesMap;
    }

    private Map<String, Object> crearMapaJpaComun() {
        Map<String, Object> jpaPropertiesMap = new HashMap<>();
        //jpaPropertiesMap.put("hibernate.max_fetch_depth", 0); comentado para ocupar el defecto
        jpaPropertiesMap.put("hibernate.connection.charSet", "UTF-8");
        jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        return jpaPropertiesMap;
    }

    @Bean
    @Qualifier("hikari")
    public DataSource dataSource(
            @Value("${app.db.jdbcurl}") String jdbcUrl,
            @Value("${app.db.user}") String user,
            @Value("${app.db.password}") String password,
            @Value("${app.db.poolsize}") int poolSize
    ) {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMaximumPoolSize(poolSize);
        return ds;
    }


}
