package com.prueba.nexos.inventario;

import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class InventarioApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventarioApplication.class, args);
  }

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
