package de.samples.blogposts.boundary;

import de.samples.blogposts.domain.BlogPostService;
import de.samples.blogposts.domain.Pagination;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/blogposts")
@RequiredArgsConstructor
public class BlogPostsController {

  private final BlogPostService service;
  private final BlogPostDtoMapper mapper;

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Stream<BlogPostDto> findAll() {
    return this.service
      .findAll()
      .map(this.mapper::map);
  }

  @GetMapping(
    path = "/pages",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  PaginatedListRessource<BlogPostDto, PaginatedListRessource.PageBasedPaginationInfo> findAllPageBased(
    @RequestParam(defaultValue = "0")
    @Min(0)
    int pageNumber,
    @RequestParam(defaultValue = "10")
    @Min(1)
    @Max(1000)
    int pageSize
  ) {
    final var pagination = Pagination.PageBased
      .builder()
      .pageNumber(pageNumber)
      .pageSize(pageSize)
      .build();
    final var blogPosts = this.service
      .findAll(pagination)
      .map(this.mapper::map);
    final var totalElements = this.service.count();
    final var maximumPageNumberLong = (totalElements + pageSize - 1) / pageSize - 1;
    if (maximumPageNumberLong > Integer.MAX_VALUE) {
      throw new ValidationException("Page size is too low, number of pages results in a number higher than " + Integer.MAX_VALUE);
    }
    final var maximumPageNumber = (int) maximumPageNumberLong;
    return PaginatedListRessource.
      <BlogPostDto, PaginatedListRessource.PageBasedPaginationInfo>builder()
      .items(blogPosts)
      .pagination(
        PaginatedListRessource.PageBasedPaginationInfo
          .builder()
          .totalElements(totalElements)
          .pageNumber(pageNumber)
          .pageSize(pageSize)
          .build()
      )
      .links(
        PaginatedListRessource.PageLinks
          .builder()
          .self(linkToPageBased(pageNumber, pageSize))
          .next(linkToPageBased(pageNumber + 1, pageSize, p -> p <= maximumPageNumber))
          .prev(linkToPageBased(pageNumber - 1, pageSize, p -> p >= 0))
          .first(linkToPageBased(0, pageSize))
          .last(linkToPageBased(maximumPageNumber, pageSize))
          .build()
      )
      .build();
  }

  private static URI linkToPageBased(int pageNumber, int pageSize) {
    return linkToPageBased(pageNumber, pageSize, n -> true);
  }

  private static URI linkToPageBased(int pageNumber, int pageSize, Predicate<Integer> pageNumberPredicate) {
    return pageNumberPredicate.test(pageNumber)
      ? linkTo(methodOn(BlogPostsController.class).findAllPageBased(pageNumber, pageSize)).toUri()
      : null;
  }


  @GetMapping(
    path = "/cursor",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  PaginatedListRessource<BlogPostDto, PaginatedListRessource.CursorBasedPaginationInfo<UUID>> findAllCursorBased(
    @RequestParam(required = false) // if null -> start with first or last
    UUID cursor,
    @RequestParam(defaultValue = "10")
    @Min(1)
    @Max(1000)
    int pageSize
  ) {
    final var pagination = Pagination.CursorBased
      .builder()
      .cursor(cursor)
      .pageSize(pageSize)
      .build();
    final var blogPosts = this.service
      .findAll(pagination)
      .map(this.mapper::map)
      .toList(); // we need the collection here to create links
    final var totalElements = this.service.count();
    return PaginatedListRessource.
      <BlogPostDto, PaginatedListRessource.CursorBasedPaginationInfo<UUID>>builder()
      .items(blogPosts.stream())
      .pagination(
        PaginatedListRessource.CursorBasedPaginationInfo
          .<UUID>builder()
          .cursor(cursor)
          .pageSize(pageSize)
          .build()
      )
      .links(
        PaginatedListRessource.PageLinks
          .builder()
          .self(linkToCursorBased(cursor, pageSize))
          .next(
            blogPosts.size() == pageSize && totalElements > pageSize
              ? linkToCursorBased(blogPosts.getLast().getUuid(), pageSize)
              : null
          )
          .first(linkToCursorBased(null, pageSize))
          .build()
      )
      .build();
  }

  private static URI linkToCursorBased(UUID cursor, int pageSize) {
    return linkTo(methodOn(BlogPostsController.class).findAllCursorBased(
      cursor,
      pageSize
    )).toUri();
  }


}
