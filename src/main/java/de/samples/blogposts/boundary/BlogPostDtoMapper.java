package de.samples.blogposts.boundary;

import de.samples.blogposts.domain.BlogPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogPostDtoMapper {

  BlogPostDto map(BlogPost source);

}
