package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.example.demo.service.CustomUserDetailsService;

/**
* Configuration class for Spring Security settings in the application.
* Configures authentication and authorization rules for different endpoints.
*/
@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@EnableWebSecurity
public class SecurityConfiguration12 extends WebSecurityConfigurerAdapter{
	
	/**
     * Array of public URLs that do not require authentication.
     */
	public static final String[] PUBLIC_URLS= {
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"webjars/**"
	};
	
	/**
     * Custom user details service for authenticating users.
     */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	/**
     * Configures HTTP security settings, authentication, and authorization rules.
     *
     * @param http HttpSecurity object to configure security settings.
     * @throws Exception If an error occurs while configuring security.
     */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.
		csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/trainer/**").hasAnyRole("TRAINER","ADMIN")
		.antMatchers("/student/**").hasAnyRole("STUDENT","ADMIN")
		.antMatchers("/LeaderBoard/**").hasAnyRole("TRAINER","ADMIN","STUDENT")
		.antMatchers(PUBLIC_URLS).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
	
	/**
     * Configures authentication manager with in-memory and custom user details service authentication.
     *
     * @param auth AuthenticationManagerBuilder object to configure authentication.
     * @throws Exception If an error occurs while configuring authentication.
     */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
    auth.inMemoryAuthentication()
		.withUser("admin")   
		.password(this.passwordEncoder().encode("admin"))
		.roles("ADMIN");
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	/**
     * Bean for creating a BCryptPasswordEncoder with strength 10.
     *
     * @return BCryptPasswordEncoder with strength 10.
     */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	}
	