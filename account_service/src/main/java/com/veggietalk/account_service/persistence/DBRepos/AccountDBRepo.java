package com.veggietalk.account_service.persistence.DBRepos;

import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDBRepo extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT a from AccountEntity as a where a.username = :username")
    Optional<AccountEntity> findByUsername(@Param("username") String username);

}
