package de.samples.blogposts.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "BlogPost")
@Table(name = "BLOG_POSTS")
@EntityListeners(AuditingEntityListener.class)
public class BlogPostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private  UUID uuid;
  @Size(min=3, max = 50)
  private  String title;
  @Size(min=3, max = 1000)
  private  String content;
  @CreatedDate
  private LocalDateTime creationDate;
  // for sorting, we use this incremented value, because creationDate is not unique!
  @OneToOne(cascade = CascadeType.ALL)
  @NotNull
  private BlogPostIndexEntity index = new BlogPostIndexEntity();

}
