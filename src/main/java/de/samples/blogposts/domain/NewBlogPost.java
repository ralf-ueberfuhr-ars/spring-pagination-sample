package de.samples.blogposts.domain;

import jakarta.validation.constraints.Size;

public record NewBlogPost(
  @Size(min = 3, max = 50) String title,
  @Size(min = 3, max = 1000) String content
) {

}
