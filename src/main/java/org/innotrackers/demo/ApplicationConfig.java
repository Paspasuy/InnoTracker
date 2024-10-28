package org.innotrackers.demo;

import org.innotrackers.demo.orm.schemas.BaseSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Configuration
@EnableJdbcRepositories
class ApplicationConfig extends AbstractJdbcConfiguration {

//    @Bean
//    DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }
    @Bean
    BeforeConvertCallback<BaseSchema> beforeConvertCallback() {
        return (schema) -> {
            if (schema.id == null) {
                schema.id = UUID.randomUUID().toString();
            }
            long time = new Date().getTime();
            if (schema.createdAt == null) {
                schema.createdAt = new Timestamp(time);
            }
            schema.updatedAt = new Timestamp(time);
            return schema;
        };
    }

    @Bean
    UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        return users;
    }

//    @Bean
//    public RequestDataValueProcessor requestDataValueProcessor() {
//        return new CsrfRequestDataValueProcessor();
//    }

}