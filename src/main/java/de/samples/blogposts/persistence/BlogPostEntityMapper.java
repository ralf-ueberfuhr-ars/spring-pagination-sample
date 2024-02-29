package de.samples.blogposts.persistence;

import de.samples.blogposts.domain.BlogPost;
import de.samples.blogposts.domain.NewBlogPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogPostEntityMapper {

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "index", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  BlogPostEntity map(NewBlogPost source);

  BlogPost map(BlogPostEntity source);

}
