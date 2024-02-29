package de.samples.blogposts.boundary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BlogPostDto {

  @NotNull
  private UUID uuid;
  @Size(min=3, max = 50)
  private String title;
  @Size(min=3, max = 1000)
  private String content;
  @NotNull
  private LocalDateTime creationDate;

}
