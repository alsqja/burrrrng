# 부르릉(Burrrrng)
### 1조 아웃소싱 프로젝트 배달앱 만들기
![image](https://github.com/user-attachments/assets/cf69b208-bab6-46a6-889e-b7cd70116882)

---
## Tech Stack
JAVA : JDK 17

Spring Boot : 3.3.5

IDE : IntelliJ

MySQL : Ver 8+

---

## **API 명세서 (팀 노션 URL)**
[URL](https://teamsparta.notion.site/1492dc3ef51481a68a9eef981c95ec60)
---

## **ERD**
<img width="1055" alt="image" src="https://github.com/user-attachments/assets/f57db9ab-6d1a-46cf-b0e2-ca5e9543cac2">


## **Wire Frame**
[URL](https://www.figma.com/design/Vte0OVVXOsEsm5KREn3Dm8/Untitled?node-id=0-1&node-type=canvas&t=OcwllmRWTwAYtEg4-0)
---

## **시연 영상**
[시연 영상 URL]()
업로드 후 추가 필요

## 팀원

| 이름              | Github 프로필  | 블로그     | 역할 |
| ----------------- | -------------- | ---------- | ---- |
| 김민범 | [alsqja]      | [블로그](https://velog.io/@alsqja2626/posts)  | 팀장 |
| 손민석 | [MinSeok3796]    | [블로그](https://usejava.tistory.com/)        | 팀원 |
| 장은영 | [eunyounging]   | [블로그](https://write7551.tistory.com/)      | 팀원 |

[eunyounging]: https://github.com/eunyounging
[alsqja]: https://github.com/alsqja
[MinSeok3796]: https://github.com/MinSeok3796

## 수행한 일

### 김민범

- API, ERD 기획 및 설계
- Entity 초기 설정 및 연관관계 생성
- GlobalExceptionHandler 구현
- 인증 / 인가 Interceptor 구현
- 일반 권한 사용자 관련 로직 구현
  - Store 조회
  - Menu 조회
  - Order 생성 및 조회
  - Review 생성 및 조회
- 장바구니 기능 구현
- 리팩토링

### 손민석

- API, ERD 기획 및 설계
- WireFrame 작성
- Owner 권한 사용자 관련 로직 구현
  - Store 조회(관리자 버전), 생성, 수정, 삭제
  - Menu 조회(관리자 버전), 생성, 수정, 삭제
  - Order 조회, 수정
- GlobalExceptionHandler 구현

### 장은영

- API, ERD 기획 및 설계
- Entity 초기 설정 및 연관관계 생성
- 인증 / 인가 관련 처리 (로그인, 로그아웃, 세션을 이용한 비밀번호 확인 후 삭제)
- User 관련 로직 구현
  - User CRUD
  - 쿠키, 세션 처리
- Soft Delete, Cascade 처리

---

## 기능

---
### 공통
- 로그인 후 서버는 클라이언트에게 JSESSIONID를 쿠키에 저장하고, 이후 클라이언트는 서버로 요청을 보낼 때마다 해당 쿠키를 자동으로 서버에 포함하게 하여, 요청마다 자동으로 인증이 이루어집니다.
- Owner 권한 요청과 일반 권한 요청을 구분하여 Interceptor 를 통해 인가가 이루어지도록 했습니다.
- 수정, 삭제 시 Session 에 저장된 로그인된 유저 정보를 통해 본인만 수정 / 삭제 할 수 있습니다.
- ControllerAdvice를 이용해 GlobalExceptionHandler 를 구현하여 통일된 에러 Response 를 message, status 등을 활용하여 반환할 수 있도록 했습니다.
- 삭제가 진행될 때 완전히 삭제하지 않고 테이블 마다 deleted_at 컬럼을 사용하여 Soft Delete 를 구현했습니다.
- 영속성 전이를 활용해 데이터가 삭제될 때 관련된 데이터가 같이 삭제될 수 있도록 했습니다.(ex. 유저가 삭제되면 유저가 생성한 가게, 주문 등이 함께 삭제됨)
- 각 테이블의 Status 혹은 Role 데이터를 Enum 을 활용해서 관리하여 안정성을 높였습니다.

### User
- 사용자를 Create, Read, Update, Delete 할 수 있습니다.
- 프로필 조회 시 민감한 정보(password) 는 조회되지 않습니다.
- 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
- 비밀번호는 암호화된 상태로 Database 에 저장됩니다.
- 이메일은 중복될 수 없습니다.
- 비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 최소 1개씩 포함해야 합니다.
- 유저 삭제 시 비밀번호 확인을 한 후 전달받은 쿠키를 포함하여 요청을 보내야 합니다.
- User 는 user, owner 의 role 을 가지고 있고, user 는 owner 권한이 필요한 요청을 수행할 수 없습니다.

### Store
- Owner 권한을 가진 사용자 만이 Store 를 Create, Update, Delete 할 수 있습니다.
- 한 명의 유저는 3개 이하의 가게만 오픈할 수 있습니다.(폐업한 가게의 경우 상관 x)
- 일반 유저는 가게의 리뷰 별점 평균, 최소 주문 금액 등을 포함한 정보 리스트를 받아볼 수 있습니다.
- Store 는 오픈시간, 마감시간 정보를 가지고 있고, 오픈 시간이 아닌 경우 주문할 수 없습니다.
- Store 는 opened, closed(폐업) 의 status 를 가지며 폐업 상태의 가게에서 주문할 수 없고 Owner 가게 개수에 count 되지 않습니다.

### Menu
- Owner 권한을 가진 사용자 만이 Menu 를 Create, Update, Delete 할 수 있습니다.
- 한 가게의 전체 메뉴 정보를 조회할 수 있습니다.
- Owner 는 본인 가게에 생성된 주문의 전체 메뉴 정보를 조회할 수 있습니다.(N : M join table order_menu 활용)
- 메뉴는 normal, soldout 의 status 를 가지며 soldout 인 메뉴는 주문할 수 없습니다.

### Cart
- 유저는 여러개의 메뉴들을 수량을 지정해 장바구니에 담을 수 있으며 수정이 가능합니다.
- 해당 카트로 주문완료 시 장바구니 정보는 삭제됩니다.
- Cart(장바구니) 는 쿠키에 encoding 되어 저장되며 하루가 지나면 자동으로 삭제됩니다.
- 장바구니에 메뉴들의 정보를 담아두고, 이를 주문할 수 있습니다.
- 서로 다른 가게의 메뉴를 담을 수 없습니다.

### Order
- 유저는 저장해둔 장바구니를 통해 상품을 주문할 수 있습니다.
- 유저는 자신이 생성한 주문내역을 mainMenu(가장 비싼 메뉴), totalPrice 등 의 정보와 함께 리스트로 받아볼 수 있습니다.
- Owner 유저는 자신의 가게에 들어온 주문 내역을 받아볼 수 있습니다.
- Order 는 unchecked, checked, cooking, cooked, riding, complete 의 status 를 가지고 있습니다.
- Owner 는 들어온 주문의 status 를 변경할 수 있습니다.

## Trouble Shooting
- [김민범 Trouble Shooting : ]()
- [손민석 Trouble Shooting : ]()
- [장은영 Trouble Shooting : ]()

## 마치며
### 완성 소감
- **김민범** : 팀원 분들 모두 맡은 파트를 잘 진행해 주시고 적극적으로 진행 내용 공유등을 해주셔서 정말 즐겁게 프로젝트를 진행 할 수 있었습니다. 아쉬웠던 점은 다음에 진행하게 된다면 기능 구현을 좀 더 생각한 ERD 작성과 Restful한 API 작성을 고민해보고 진행해야 겠다 생각합니다. 한 주 동안의 프로젝트 였지만 정말 재밌게 코딩하고 많이 배울 수 있었습니다!
- **손민석** : 긍정적인 팀 프로젝트 경험이 되었습니다. 팀장님이 잘 리드 해주셔서 다른 팀원들도 잘 따라갈 수 있었지않나 생각이 들기도 합니다. 설계부터 구현하여 테스트까지 각자 맡은 부분에 열심히 하여 각 기능이 제대로 돌아가도록 만드는 재밌는 프로젝트가 되었던것 같습니다.
- **장은영** : 항상 적극적으로 도움을 주시는 팀원분들 덕분에 정말 많은 것들을 배운 프로젝트였습니다. 팀원분들께서 언제나 빠른 피드백을 주셨고, 의사소통이 원활했기에 효율적으로 진행이 되었던 것 같습니다. 많이 부족한 저를 늘 열정적으로 이끌어 주신 팀원분들께 정말 감사드립니다. 

### 아쉬웠던 점
- **김민범** : 초기 기획 단계가 중요한 것을 잘 알고 신경써서 기획을 했지만, 개발을 진행하며 여러 문제들이 발생했던 부분이 아쉬웠습니다. 도전 과제에 재미있어 보이는 내용이 많았는데 불경기에 인원 감축을 당해 시간이 부족하여 구현하지 못해 아쉽습니다.
- **손민석** : 처음에 어디까지 구현을 할지 미리 도전기능까지 생각을 해서 설계를 했지만 이러한 방법은 그렇게 좋지 않다는 피드백을 받아서 다음 프로젝트 부터는 필수기능에 충실히 이행을 한 뒤에 추가적으로 도전기능을 생각하며 확장해나가는 시간을 가져보고싶다.
- **장은영** : 이번 프로젝트는 하나부터 열까지 다 팀원분들의 도움이 없었다면 불가능했을 정도로 제게는 프로젝트 관련 경험과 지식이 많이 부족한 상태였습니다. 특히 학습이 충분히 되지 못해서 구현 부분이 많이 어려웠고, 다른 팀원분들의 진행 상황을 듣고도 그것이 어떤 내용인지 이해가 가지 않는 경우도 있었습니다. 이번 프로젝트 이후에 공부를 더 많이 해서 다음 프로젝트 때에는 조금 더 다양한 기능을 구현해보고 싶습니다.        










