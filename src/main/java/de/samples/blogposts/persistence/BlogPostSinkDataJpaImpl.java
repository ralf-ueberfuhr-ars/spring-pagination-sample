package de.samples.blogposts.persistence;

import de.samples.blogposts.domain.BlogPost;
import de.samples.blogposts.domain.BlogPostSink;
import de.samples.blogposts.domain.NewBlogPost;
import de.samples.blogposts.domain.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.PageRequest.ofSize;

@Component
@RequiredArgsConstructor
public class BlogPostSinkDataJpaImpl implements BlogPostSink {

  private final BlogPostEntityRepository repo;
  private final BlogPostEntityMapper mapper;

  @Override
  public long count() {
    return this.repo.count();
  }

  @Override
  public void create(NewBlogPost blogPost) {
    this.repo.save(this.mapper.map(blogPost));
  }

  @Override
  public Stream<BlogPost> findAll() {
    return this.repo
      .findAll()
      .stream()
      .map(this.mapper::map);
  }

  @Override
  public Stream<BlogPost> findAll(Pagination.PageBased pagination) {
    final var pageable = of(
      pagination.getPageNumber(),
      pagination.getPageSize()
    )
      .withSort(
        Sort
          .by("index")
          .descending()
      );
    return this.repo
      .findAll(pageable)
      .stream()
      .map(this.mapper::map);
  }

  @Override
  public Stream<BlogPost> findAll(Pagination.CursorBased pagination) {
    final var pageable = ofSize(
      pagination.getPageSize()
    ).withSort(
      Sort
        .by("index")
        .descending()
    );
    final var cursorIndex = Optional.ofNullable(pagination.getCursor())
      .flatMap(this.repo::findById)
      .map(BlogPostEntity::getIndex)
      .map(BlogPostIndexEntity::getValue);
    // we could solve this by Spring Data's repository extension method feature too.
    final Specification<BlogPostEntity> spec =
      (root, query, criteriaBuilder) -> cursorIndex
        .map(i -> criteriaBuilder.lessThan(root.get("index").get("value"), i))
        .orElse(null);
    return this.repo
      .findAll(
        spec,
        pageable
      )
      .stream()
      .map(this.mapper::map);
  }
}
