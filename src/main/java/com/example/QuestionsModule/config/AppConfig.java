package com.example.QuestionsModule.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
//@EnableWebSecurity
public class AppConfig {
	@Autowired
	DataSource dataSource;
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	@Autowired
//	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
//	}
//	@Bean
//	public JdbcUserDetailsManager jdbcUserDetailsManager()throws Exception{
//		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager();
//		jdbcUserDetailsManager.setDataSource(dataSource);
//		return jdbcUserDetailsManager;
//	}
//	@Override
//	public void configure(WebSecurity web)throws Exception{
//		web.ignoring().antMatchers("/resources/**");
//	}
}
