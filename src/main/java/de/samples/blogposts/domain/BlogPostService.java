package de.samples.blogposts.domain;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
public class BlogPostService {

  private final BlogPostSink sink;

  public long count() {
    return sink.count();
  }

  public void create(@Valid NewBlogPost blogPost) {
    sink.create(blogPost);
  }

  public Stream<BlogPost> findAll() {
    return sink.findAll();
  }

  public Stream<BlogPost> findAll(@Valid Pagination.PageBased pagination) {
    return sink.findAll(pagination);
  }

  public Stream<BlogPost> findAll(@Valid Pagination.CursorBased pagination) {
    return sink.findAll(pagination);
  }

}
