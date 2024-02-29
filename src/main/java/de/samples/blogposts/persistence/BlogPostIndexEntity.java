package de.samples.blogposts.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "BlogPostIndex")
@Table(name = "BLOG_POST_INDEX")
@Getter
@Setter
public class BlogPostIndexEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  Long value;

}
