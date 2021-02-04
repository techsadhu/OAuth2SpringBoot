package com.example.zuuldemo;

import com.example.zuuldemo.filter.OktaAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableZuulProxy
@EnableDiscoveryClient
@EnableWebSecurity
@SpringBootApplication
public class ZuuldemoApplication {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(ZuuldemoApplication.class, args);
	}

	@Configuration
	public class SecurityConfig extends WebSecurityConfigurerAdapter
	{
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			http.cors()
					.configurationSource(request -> {
						CorsConfiguration cors = new CorsConfiguration();
						cors.setAllowedOrigins(Arrays.asList("*"));
						cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
						cors.setAllowedHeaders(Arrays.asList("*"));
						cors.setExposedHeaders(Arrays.asList("Authorization"));

						//cors.checkOrigin("http://localhost:8080");
						logger.debug("==>>> configurationSource CORS:"+cors.toString());
				return cors;}).and()
			//http
					.csrf().disable()
					.authorizeRequests().anyRequest().authenticated()
					.and()
					//.cors().and()
					.oauth2Login()
					.and()
					.oauth2ResourceServer().jwt();
		}
	}

/*	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setExposedHeaders(Arrays.asList("Authorization"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}*/

	@Bean
	public OktaAuthenticationFilter oktaAuthFilter(OAuth2AuthorizedClientService clientService) {
		return new OktaAuthenticationFilter(clientService);
	}

}
