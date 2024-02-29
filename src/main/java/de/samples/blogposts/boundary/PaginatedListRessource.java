package de.samples.blogposts.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.net.URI;
import java.util.stream.Stream;

@Getter
@Builder
public class PaginatedListRessource<ItemType, PaginationType extends PaginatedListRessource.PaginationInfo> {

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
  public static class CursorBasedPaginationInfo<CursorType> implements PaginationInfo {

    private final CursorType cursor;
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

  private final Stream<ItemType> items;
  private final PaginationType pagination;
  @JsonProperty("_links")
  private final PageLinks links;

}
