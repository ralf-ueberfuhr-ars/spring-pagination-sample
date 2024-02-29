package de.samples.blogposts.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogPostEntityRepository
  extends JpaRepository<BlogPostEntity, UUID>, JpaSpecificationExecutor<BlogPostEntity> {

}
