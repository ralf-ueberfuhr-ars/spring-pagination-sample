package de.samples.blogposts.domain;

import java.util.stream.Stream;

public interface BlogPostSink {

  long count();

  void create(NewBlogPost blogPost);

  Stream<BlogPost> findAll();

  Stream<BlogPost> findAll(Pagination.PageBased pagination);

  Stream<BlogPost> findAll(Pagination.CursorBased pagination);


}
