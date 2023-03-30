package com.EaseAmuse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {
	private static final String[] AUTH_WHITE_LIST = { "/v3/api-docs/**", "/swagger-ui/**", "/v2/api-docs/**",
			"/swagger-resources/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers(AUTH_WHITE_LIST).permitAll()
				.requestMatchers(HttpMethod.POST, "/api/managers/", "/api/admins/", "/api/customers/").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/admins/signIn", "/api/managers/signIn", "/api/customers/signIn")
				.permitAll()
//				.requestMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CUSTOMER")
//				.requestMatchers(HttpMethod.GET, "/api/managers/**").hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
				.anyRequest().authenticated().and()
				.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class).formLogin().and()
				.httpBasic();

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

}
