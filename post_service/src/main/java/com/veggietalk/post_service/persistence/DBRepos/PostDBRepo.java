package com.veggietalk.post_service.persistence.DBRepos;

import com.veggietalk.post_service.persistence.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDBRepo extends JpaRepository<PostEntity, Long> {
}
