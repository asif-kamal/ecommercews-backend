package com.electronics.pgdata.auth.repository;

import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountUserDetailRepository extends JpaRepository<AccountUser, Long> {
}
