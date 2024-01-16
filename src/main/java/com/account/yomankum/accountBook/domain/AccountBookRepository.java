package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.AccountBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findByCreateUser(String userId);

}
