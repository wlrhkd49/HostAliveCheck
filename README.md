# 호스트들의 Alive 상태 체크 및 모니터링 API 서버 개발

## 호스트 등록 관리 REST API
### 호스트 등록
**param으로 name, ip를 요청받고 호스트 등록을 수행한다.**
- HTTP Method: POST
- Params: name, ip
- Request URL: localhost:8080/api/host?name={name}&ip={ip}
<img width="845" alt="스크린샷 2022-05-21 오후 4 24 37" src="https://user-images.githubusercontent.com/63788023/169640842-239466ec-58cb-4a2b-833e-4ad5cd16444d.png">

### 호스트 조회
**등록된 전체 호스트의 이름, ip, 마지막 Alive 시간, 등록일, 수정일을 조회한다.**
- HTTP Method: GET
- Request URL: localhost:8080/api/host
<img width="865" alt="스크린샷 2022-05-21 오후 4 26 04" src="https://user-images.githubusercontent.com/63788023/169640892-b7eeffb2-663e-4082-bfae-4358bc87d068.png">

### 호스트 수정
**param을 이용하여 해당하는 호스트를 찾고 body로 요청된 정보로 호스트 정보를 수정한다.**
- HTTP Method: PUT
- Params: name, ip
- RequestBody: "name":"수정 이름", "ip":"수정 ip"
- Request URL: localhost:8080/api/host?name={name}&ip={ip}
<img width="846" alt="스크린샷 2022-05-21 오후 4 27 20" src="https://user-images.githubusercontent.com/63788023/169640941-76cf8c50-0417-4d58-9498-d20dfd023788.png">


### 호스트 삭제
**param을 이용해여 해당하는 호스트를 찾고 해당 호스트를 DB에서 삭제한다.**
- HTTP Method: DELETE
- Params: name, ip
- Request URL: localhost:8080/api/host?name={name}&ip={ip}
<img width="853" alt="스크린샷 2022-05-21 오후 4 28 41" src="https://user-images.githubusercontent.com/63788023/169640978-bb55b7ba-aa78-402a-b74c-7adc436e69ea.png">


## 특정 호스트의 현재 Alive 상태 조회 REST API
**RequestBody를 통해 요청한 호스트의 alive 상태를 InetAddress.isReachable() 메서드를 이용하여 조회한다.**

**조회된 결과는 호스트이름, 호스트ip, 마지막 Alive 시간, Alive 상태를 표시한다.**
- HTTP Method: GET
- RequestBody: "name":"조회하고싶은 이름", "ip":"조회하고싶은 ip"
- Request URL: localhost:8080/api/host/alive
<img width="848" alt="스크린샷 2022-05-21 오후 4 29 45" src="https://user-images.githubusercontent.com/63788023/169641020-65e63fd9-0989-467d-a298-c7895607f80e.png">

## 호스트들의 Alive 모니터링 결과 조회 REST API
**Enum(Criteria)를 param으로 받아 한 호스트 조회 or 모든 호스트 조회를 결정**

**한 호스트 조회일 경우 = (criteria == ONE) 조회할 호스트 body로 요청 받음**

**모든 호스트 조회일 경우 = (criteria == ALL) <br/> 전체 조회 시 100개의 호스트가 모두 Unreachable 상태여도 조회는 1초 이내에 응답해야하므로 InetAddress.isReachable()의 timeout을 10ms로 설정하여 100개 조회를 1초안에 수행할 수 있도록 한다.**
- HTTP Method: GET
- Params: Criteria
- criteria가 ONE인 경우 
  - RequestBody: "name":"조회하고싶은 이름", "ip":"조회하고싶은 ip"
- criteria가 ALL인 경우
  - RequestBody 없음
- Request URL: localhost:8080/api/host/alive/list?criteria={criteria}
<img width="851" alt="스크린샷 2022-05-21 오후 4 31 39" src="https://user-images.githubusercontent.com/63788023/169641078-053d2130-ffcc-4598-a2a8-7c9764d394d0.png">
<img width="863" alt="스크린샷 2022-05-21 오후 4 30 46" src="https://user-images.githubusercontent.com/63788023/169641054-8e59290f-3cc0-4541-b07a-2acda3316d00.png">
