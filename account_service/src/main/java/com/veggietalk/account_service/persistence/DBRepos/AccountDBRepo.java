package com.veggietalk.account_service.persistence.DBRepos;

import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDBRepo extends JpaRepository<AccountEntity, Long> {
}
