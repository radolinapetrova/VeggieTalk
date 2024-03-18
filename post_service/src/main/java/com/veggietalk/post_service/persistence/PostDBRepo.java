package com.veggietalk.post_service.persistence;

import com.veggietalk.post_service.persistence.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDBRepo extends JpaRepository<PostEntity, Long> {
}
