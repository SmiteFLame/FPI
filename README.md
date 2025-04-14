# (FPI) Free Point Information

## ERD
![ERD](/img/ERD.png)

### Database 확인 방법
- http://localhost:8080/h2-console 접속
- JDBC URL: jdbc:h2:mem:pointdb
- User Name: sa
- Password: 빈값
- Connect으로 접속

### ERD
- User – 사용자 정보
- UserPoint – 사용자별 포인트 상한
- Point – 적립된 개별 포인트 정보
- PointHistory – 모든 포인트 변경 이벤트 이력 저장
- PointRequest – 사용 요청의 기준이 되는 객체
- Configuration – 최대 적립 포인트 및 만료일 기본값 등의 시스템 설정

### User /  UserPoint
- User 에 대한 기본 설정 값을 추가하는 API입니다.
- Warmup을 통해서 테스트 케이스를 추가하였습니다.

#### UserPoint
- 개인별 보유 가능한 무료 포인트 최대금액 제한을 추가하였습니다.
- User 별로 설정이 가능하도록 설정하였으며, 추후 추가 제한 / 기능을 고려하여 테이블을 분리하였습니다.

### Configuration
- 1회 최대적립 가능 포인트 등 다양한 하드코딩을 제어할 수 있는 Configuration 입니다.
- 지금은 `MAX_AMOUNT_PER_ONCE` 값만 추가하였지만 확장 가능하도록 고려

### Point
- Point: Point 적립 / 적립 취소 시에 사용되는 메인 테이블
- PointRequest: Point 사용 / 사용 취소에 사용되는 메인 테이블
- PointHistory: 모든 Case에 대한 Log를 저장하는 테이블


## 추가로 고려해야 하는 사항

### 유저별 보유 가능한 무료포인트 최대금액
- 유저별 보유 가능한 최대 금액을 가지고 있을때
- 기존 사용된 포인트를 취소하는 경우 최대 금액을 넘을 수 있다.
- 이때, 무료포인트를 추가할 수 있는지

#### CASE-A: 포인트 롤백 / 신규 추가는 하지 않는다.
#### CASE-B: 포인트랑 신규 포인트 적립도 진행한다.

### 신규 적립 만료일 기준
- 사용취소를 한 건 중에서 이미 만료일이 지난 건이 있는 경우 신규 적립이 필요하다.
- 이때, 신규 적립건의 만료일자 기준은 어떻게 되는지?

#### CASE-A: 이전 만료일자 기준으로 + N년을 더한다.
#### CASE-B: now()으로 부터 365(Default) 혹은 특정 날짜를 더한다.
