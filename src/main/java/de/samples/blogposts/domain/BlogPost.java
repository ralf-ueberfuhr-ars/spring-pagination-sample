package de.samples.blogposts.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class BlogPost {

  @NotNull
  private final UUID uuid;
  @Size(min = 3, max = 50)
  private final String title;
  @Size(min = 3, max = 1000)
  private final String content;
  @NotNull
  @Builder.Default
  private LocalDateTime creationDate = LocalDateTime.now();

}
