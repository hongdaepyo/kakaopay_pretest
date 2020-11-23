# kakaopay_pretest

# 카카오페이 사전과제 : 카카오페이 뿌리기 기능 구현하기
-------------

### UI 및 메시지 영역은 제외한 간소화된 REST API를 구현하기.

### 개발환경
- IDE: 이클립스
- OS: Windows 10
- 버전관리 : GIT

###  server
- Spring Boot 2.4.0
- JPA
- H2
- JUnit 5
- Gradle
- Lombok
-------------
### Build and Run App
`git clone https://github.com/hongdaepyo/kakaopay_pretest.git`
`cd kakaopay_pretest/`
`./gradlew clean build`
`java -jar build/libs/kakaopaySprinkle-0.0.1-SNAPSHOT.jar`

-----------------------------------
### api specifications
1. 뿌리기
POST /kakaopay/sprinkle 
- url : http://127.0.0.1:8080/kakaopay/sprinkle
- request
  - headers 에 추가.
  X-USER-ID : 00001
  X-ROOM-ID : ABCDE
 - body
```json
{
    "amount": 200000,
    "memberCount": 5
}
```
- response (랜덤으로 만들어진 토큰)
IEX

---------------------

2. 받기
PUT /kakaopay/sprinkle
- url : http://127.0.0.1:8080/kakaopay/sprinkle
- request
   - headers에 추가.
   X-USER-ID : 00002
   X-ROOM-ID : ABCDE
   - body
   ```json
{
    "token": "IEX"
}
```
- response (랜덤으로 분배된 뿌리기 금액)
11

----------------------

3. 조회
GET /kakaopay/checkmoney/{token}
- url : http://127.0.0.1:8080/kakaopay/checkmoney/{token}
- request
 - path value : IEX
 - /kakaopay/checkmoney/IEX
 
- response
```json
{
    "totalMoney": 200000,
    "receivedInfo": [
        {
            "receiverId": "00002",
            "receivedMoney": 23
        },
        {
            "receiverId": "00003",
            "receivedMoney": 11
        }
    ],
    "totalReceivedMoney": 34,
    "createDate": "2020-11-23T18:25:17.706"
}
```
--------------------------------------

### 핵심 문제해결 전략
------------------------
#### 뿌리기
- 토큰 생성
 - 영어 대문자 + 영어 소문자 + 숫자를 결합한 문자열을 만든 후, Java.security.SecureRandom 클래스를 이용하여 랜덤하게 인덱스를 만든다. 만들어진 인덱스를 charAt에 사용하여 3자리의 랜덤 문자열을 생성한다.
- 뿌리기 금액 분배
 - 분배 전 금액을 뿌린 금액과 같게 초기화한다.
   - long remainMoney = totalAmount;
 - SecureRandom 클래스를 이용하여 random long value를 만들고 remainMoney 에서 빼준다. 이 때 long value는 remainMoney로 나눈 나머지를 구하게 하여 remainMoney보다 크지 않은 무작위 값을 뺄 수 있도록 한다.
   - long randomMoney = Math.abs(random.nextLong() % remainMoney);
   - 위에서 구해진 randomMoney를 long 배열에 담고 그 배열을 shuffle하여 무작위 뿌린 금액 배열을 만들어 저장한다. shuffle 함수에도 random 으로 인덱스 두개를 생성한 뒤 배열에서 해당 인덱스의 값을 스왑한다.
 - sprinkle/util/common.util 참고

--------------

#### 받기
- 제약조건 처리
 - 뿌리기 당 사용자는 한번만 받을 수 있다
   - JPA를 활용하여 현재 방에서 받기 요청한 사용자가 요청 토큰으로 받은 내역이 있는지 확인.
 - 자신이 뿌리기한 건은 자신이 받을 수 없다.
   - db에서 받아온 데이터에서 뿌리기를 등록한 사용자와 현재 받기 요청한 사용자의 아이디를 도메인에서 비교
 - 뿌리기 호출된 대화방과 동일한 대화방에 속한 사용자만 받을 수 있다.
   - JPA로 조회 시 By 조건절에 RoomId 항목을 추가하여 현재 방의 뿌리기만 조회하도록 한다.
 - 뿌린 건은 10분간만 유효합니다.
   - db에서 받아온 데이터에서 현재 시간과 뿌리기가 생성된 시간을 비교.
   
-----------------------------------------------

#### 조회
- 제약조건 처리
  - 뿌린 사람 자신만 조회할 수 있습니다.
    - JPA로 조회 시 By 조건절에 UserId를 넣어 현재 요청한 사용자의 뿌리기 내역만 조회한다.
  - 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.
    - JPA로 조회 시 By 조건절에 createDateBetween을 넣어 오늘부터 일주일 전까지의 데이터를 조회한다.

