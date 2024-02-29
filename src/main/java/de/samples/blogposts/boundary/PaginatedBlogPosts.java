package de.samples.blogposts.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Builder
public class PaginatedBlogPosts {

  public interface PaginationInfo {

  }

  @Getter
  @Builder
  public static class PageBasedPaginationInfo implements PaginationInfo {

    private final long totalElements;
    private final int pageNumber;
    private final int pageSize;

  }

  @Getter
  @Builder
  public static class CursorBasedPaginationInfo implements PaginationInfo {

    private final UUID cursor;
    private final int pageSize;

  }

  @Getter
  @Builder
  public static class PageLinks {
    private final URI self;
    private final URI next;
    private final URI prev;
    private final URI first;
    private final URI last;
  }

  private final Stream<BlogPostDto> items;
  private final PaginationInfo pagination;
  @JsonProperty("_links")
  private final PageLinks links;

}
