package de.samples.blogposts.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Pagination {

  @Builder
  @Getter
  public class PageBased {

    @Min(0)
    private final int pageNumber;
    @Min(1)
    @Max(1000)
    private final int pageSize;

  }

  @Builder
  @Getter
  public class CursorBased {

    private final UUID cursor;
    @Min(1)
    @Max(1000)
    private final int pageSize;

  }

}
