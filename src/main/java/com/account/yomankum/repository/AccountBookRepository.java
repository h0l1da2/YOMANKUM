package com.account.yomankum.repository;

import com.account.yomankum.domain.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
}
