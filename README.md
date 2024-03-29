# (가제) 요만큼
---

[자세한 건 **여기**를 클릭해주세요!](https://larshavin.notion.site/a42a9b4cfa1f4555bb4fd2dd2e009119?pvs=4)


---

## 요약

내가 어디에 얼마 썼더라? 가계부를 편리하게 정리하자!

## 기존 사용자들의 불만

왜 연동이 되는 편한 가계부 어플이 있는데도 수기로 가계부를 작성하시나요?

- 카드 내역을 긁어오는 것이 편리하긴 하지만 정작 구체적으로 “어디에” 썼는진 모를 때가 있다.
- 직관적이지 않고 카테고리를 만들 수가 없다. (있어도 숫자 뭐 그런… 별루다)
- 문자로 대출 내역이 와도 가계부가 인식해버려서 귀찮다.
- 실사용 내역이랑 안 맞는 경우가 있다.

## 기능

- 월급 입력이 가능하다.
- 오늘 소비, 일주일 소비, 월소비 등을 분류하자.
- 내가 결제한 내역을 태그를 붙여 분류하자.
- 태그를 클릭하면 관련 구매 내역들이 나온다.
  - 전체 얼마를 썼는지?
  - 몇 번 구매했는지?
  - 월급의 몇 퍼센트를 투자했는지?
- 이전(월 별) 결제 내역과 얼마나 차이가 있는지 조사한다. (그래프)

→ 나중에 실 사용자가 많다면 해당 월급을 받는 사람들 중, 몇 퍼센트가 음식에 돈을 쓰는지, 할부 계산 등을 보여줘도 좋겠다.

## 장점

- 내가 어디에 돈을 얼마나 썼는지 확인 가능.
- 특정 소비 비율 확인 가능.
- 어디에 소비가 많고 적음을 알아서 용돈 분배를 잘 할 수 있을듯.
- 할부도 계산 가능.

## 단점

- 하나하나 수기 작성이 귀찮다.

## 단점을 보완

- 그럼에도 수기 작성을 원하는 사람은 직관적인 애플리케이션이 필요하기 때문이다.
- ui 와 기능(카테고리 분류)을 직관적으로 만들어 편리하게 사용 가능하게 한다.



---



## 🐳 Backend

- [JWT + OAuth2 인증](https://hyuil.tistory.com/193)
  - 공개 키 검증하는 JwtValue 클래스에 전략패턴 이용.
  - `kid` 값이 첫 번째 공개키와 일치하면 `~FirstJwt` 두 번째와 일치하면 `~SecondJwt` 객체 사용.
  - `OAuth2AuthorizationRequestResolver` 직접 구현으로 이전 페이지로 Redirection 가능
- [사용자 정의 예외로 에러 핸들링](https://github.com/h0l1da2/MEMO-RE_BE/tree/master/src/main/java/sori/jakku/kkunkkyu/memore/common/exception)
  - `BadRequestException` → 4xx 에러
  - `InternalErrorException` → 5xx 에러
  - `Exception` → 에러 코드
- [비밀번호 자체 검증 애노테이션 사용](https://hyuil.tistory.com/288)
- Docker container image → container 생성
  - 빠른 빌드를 위해 `./gradlew build --exclude-task test` 로 테스트 비포함 빌드
- Api 명세서 → Swagger 사용