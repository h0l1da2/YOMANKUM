package com.account.yomankum.accountBook.domain.tag;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainTagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByAccountBookId(Long accountBookId);

}
