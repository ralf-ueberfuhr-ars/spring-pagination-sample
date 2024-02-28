package de.samples.blogposts.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("NullableProblems")
@Configuration
public class IndexPageConfiguration {

  @Bean
  WebMvcConfigurer indexPageConfig() {
    return new WebMvcConfigurer() {
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
        registry
          .addViewController("/")
          .setViewName("redirect:/swagger-ui.html");
      }
    };
  }

}
