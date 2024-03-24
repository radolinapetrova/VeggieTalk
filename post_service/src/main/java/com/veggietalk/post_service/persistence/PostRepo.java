package com.veggietalk.post_service.persistence;

import com.veggietalk.post_service.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo {
    Post save(Post post);
}
