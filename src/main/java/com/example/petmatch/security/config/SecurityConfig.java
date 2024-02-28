package com.example.petmatch.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import com.example.petmatch.config.jwt.JwtUtils;
import com.example.petmatch.security.config.filter.JwtAuthenticationFilter;
import com.example.petmatch.security.config.filter.JwtAuthorizationFilter;
import com.example.petmatch.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain filterChain (HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		return httpSecurity
			.csrf(config -> config.disable())
			.authorizeHttpRequests(authorize -> {
				authorize.requestMatchers("/listarproductosporid", "/listarmascotas", "/registrarmascota/**", "/registrarusuario/**", "/delete/**", "/usuariopornombre/**").permitAll();
				authorize.anyRequest().authenticated();
			})
			.sessionManagement(session -> {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			.addFilter(jwtAuthenticationFilter)
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.cors(withDefaults())
			.logout(form -> form
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					)
			.build();
			
			/*.httpBasic(withDefaults())
			.logout(form -> form
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					)
			.formLogin(form -> form
					.loginPage("/login")
					.loginProcessingUrl("/logginprocess")
					.permitAll()
					);*/
			
	}
	
	
	/*
	 * @Bean UserDetailsService userDetailsService() { InMemoryUserDetailsManager
	 * manager = new InMemoryUserDetailsManager();
	 * manager.createUser(User.withUsername("cristian") .password("123") .roles()
	 * .build()); return manager; }
	 */
	
	@SuppressWarnings("deprecation")
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder ) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(passwordEncoder).and()
				.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

}
