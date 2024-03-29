package com.veggietalk.post_service.persistence.DBRepos;

import com.veggietalk.post_service.persistence.model.MediaFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileDBRepo extends JpaRepository<MediaFileEntity, Long> {
}
