package com.example.Jpademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class JpademoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpademoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000") // Adjust this to your frontend's URL
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowCredentials(true);
			}
		};
	}

	// @Bean
    // public Configuration freemarkerConfig() {
    //     Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
    //     configuration.setClassForTemplateLoading(this.getClass(), "/");
    //     return configuration;
    // }

//	@Bean
//	public CommandLineRunner demo(DetailRepository detailRepository) {
//		return args -> {
//			// Create a new Detail entity
//			Detail detail = new Detail();
////			// Set properties for the entity
//			detailRepository.save(detail);
////
//			// Retrieve details from the database
//			Detail retrievedDetail = detailRepository.findById(detail.getId()).orElse(null);
//
//			System.out.println("Retrieved Detail: " + retrievedDetail);
//		};
//	}
}

