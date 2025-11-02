package com.example.nkserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class NkserverApplication {

	@Value("${cors.allowed-origin}")
	private String allowedOrigin;

	public static void main(String[] args) {
		SpringApplication.run(NkserverApplication.class, args);
	}

//	@Bean
//	public CorsFilter corsFilter() {
//
//		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		List<String> allowedOriginsList = Arrays.asList(allowedOrigin.split(","));
////		corsConfiguration.setAllowedOrigins(allowedOriginsList);
//		corsConfiguration.setAllowedOrigins(Arrays.asList( "http://localhost:5000","http://localhost:3000","http://78.96.25.131:5000"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
//				"Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Headers"));
//		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
//				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//		return new CorsFilter(urlBasedCorsConfigurationSource);
//	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// ✅ permite cookies / auth headers
		config.setAllowCredentials(true);

		// ✅ origini exacte - include și portul Vite
		config.setAllowedOrigins(Arrays.asList(
				"http://localhost:5175",  // Vite default port
				"http://localhost:3000",  // pentru CRA
				"http://127.0.0.1:5175",
				"http://localhost:5000"
		));

		// ✅ metode HTTP permise
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// ✅ headere acceptate
		config.setAllowedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Authorization", "Jwt-Token",
				"X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"
		));

		// ✅ headere expuse către browser
		config.setExposedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
		));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}



}
