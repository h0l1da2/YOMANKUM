package com.account.yomankum.accountBook.domain.record;

import com.account.yomankum.accountBook.domain.record.repository.RecordCustomRepository;
import com.account.yomankum.accountBook.domain.record.repository.RecordCustomRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DDD 관점 상 record 는 accountBook 에그리거트에 속하는 엔티티이므로 accountBookRepo 를 통해 데이터를 관리하는 것이 개념적으로 맞지만,
 * 서비스의 주요 데이터이며 검색조건, 페이지네이션 (혹은 무한그리드)이 가능해야 하기에 별도의 repo 에서 접근하도록 한다.
  */
public interface RecordRepository extends JpaRepository<Record, Long>, RecordCustomRepository {

}
