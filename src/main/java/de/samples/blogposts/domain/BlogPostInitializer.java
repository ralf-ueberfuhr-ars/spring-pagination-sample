package de.samples.blogposts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BlogPostInitializer {

  private final BlogPostService service;


  @EventListener(ContextRefreshedEvent.class)
  void initializeSamples() {
    if (this.service.count() < 1) {
      IntStream.range(1, 1000)
        .mapToObj(
          index ->
            new NewBlogPost(
              "Blog Post #" + index,
              "This is a sample blog post that is created automatically."
            )
        )
        .forEach(this.service::create);
    }

  }

}
