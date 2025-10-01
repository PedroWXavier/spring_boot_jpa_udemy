package org.example.spring_boot_data_udemy.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    private final DatabaseVariables databaseVariables;

    public DatabaseConfiguration(DatabaseVariables databaseVariables){
        this.databaseVariables = databaseVariables;
    }

//    @Bean
//    public DataSource dataSource() {
////        Nao utilizar DriverManagerDataSource em projetos reais de larga escala
////        O padrao a ser utilizado eh a o Hikari datasource, que pode gerenciar um pool de conexoes
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(databaseVariables.getUrl());
//        ds.setUsername(databaseVariables.getUsername());
//        ds.setPassword(databaseVariables.getPassword());
//        ds.setDriverClassName(databaseVariables.getDriverClassName());
//        return ds;
//    }

    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseVariables.getUrl());
        config.setUsername(databaseVariables.getUsername());
        config.setPassword(databaseVariables.getPassword());
        config.setDriverClassName(databaseVariables.getDriverClassName());

        config.setMaximumPoolSize(10); // maximo de conexoes liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("hikari-pool");
        config.setMaxLifetime(600000); // 10 minutos, tempo maximo de vida de uma conexao
        config.setConnectionTimeout(100000); // tempo maximo de espera para obter uma conexao


        return new HikariDataSource(config);
    }
}
