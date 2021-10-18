# 포털형 CMS

## 개요
- 포털, 홈페이지를 구축할때 템플릿 형식으로 가져다 사용할 수 있는 웹어플리케이션
- 쉽고 편리하게 관리할 수 있는 Admin 을 제공
- 백엔드와 프론트엔드를 분리
- Cloud Native 하도록 설계하여 개발

### 사용기술
- Spring Boot 2
- Spring Data JPA
- Spring Security, JWT
- Mysql 5.7
- Flyway
- jUnit5, RestAssured

### Admin 기능 목록

- 인증
    - [x] Spring Security 설정
    - [x] JWT 인증 구현
    - [x] refresh token 구현
- 인가
    - [x] 인가 처리 DB 연동
- 인증 및 인가 테스트
  - [x] 로그인시 JWT 토큰 발급
  - [x] 로그인 후 권한이 있는 리소스 요청
  - [x] 로그인 후 권한이 없는 리소스 요청
- 사용자계정
    - [x] 사용자계정 목록을 조회한다.
    - [x] 사용자계정을 등록한다.
        - [x] ID 유효성 검증
            - [x] ID는 필수입력항목
            - [x] ID는 영문으로 시작해야함
            - [x] ID는 영문과 숫자로 이루어져 있어야함
            - [x] ID는 5~15자이내의 문자열이어야함
        - [x] 비밀번호 유효성 검증
            - [x] 8~20자이내
            - [x] 문자, 숫자, 특수문자의 조합으로 8자리 이상으로
        - [x] 사용자의 기본권한(USER)을 설정한다.
    - [x] 사용자를 수정한다.
    - [x] 사용자를 삭제한다.
- 사이트
    - [ ] 사이트 목록을 조회한다.
    - [ ] 사이트를 등록한다.
    - [ ] 사이트를 수정한다.
    - [ ] 사이트를 삭제한다.
- 공통코드
    - [ ] 공통코드 목록을 조회한다.
    - [ ] 공통코드를 등록한다.
    - [ ] 공통코드를 수정한다.
    - [ ] 공통코드를 삭제한다.
- 공통코드상세
    - [ ] 공통코드상세 목록을 조회한다.
    - [ ] 공통코드상세를 등록한다.
        - [ ] 상위 공통코드를 선택시 코드순번(next)을 자동입력 해준다.
    - [ ] 공통코드상세를 수정한다.
    - [ ] 공통코드상세를 삭제한다.
- 권한
    - [ ] 권한목록을 조회한다.
- 롤
    - [ ] 롤 목록을 조회한다.
    - [ ] 록을 등록한다.
        - [ ] 롤 순번(next)을 자동입력 해준다.
    - [ ] 록을 수정한다.
    - [ ] 록을 삭제한다.
- 권한롤매핑
    - [ ] 권한목록을 조회한다.
    - [ ] 권한별 롤매핑 목록을 조회한다.
        - [ ] 권한별 롤매핑을 수정한다.
        - [ ] 다중선택하여 롤매핑하는 기능을 제공한다.
- 메뉴
    - [ ] 트리메뉴를 이용한 메뉴탐색기능을 제공한다.
    - [ ] 트리메뉴에서 메뉴명변경/이동/삭제 기능을 제공한다.
    - [ ] 메뉴선택시 해당메뉴의 설정화면을 제공한다.
    - [ ] 메뉴유형에 따라 입력선택기능을 제공한다.
        - [ ] 컨텐츠인 경우 : 컨텐츠 검색 입력
        - [ ] 게시판인 경우 : 게시판 검색 입력
        - [ ] 내부링크 : url 입력란 제공
        - [ ] 외부링크 : url 입력란 제공
- 권한별메뉴
    - [ ] 권한목록이 탭메뉴로 제공된다.
    - [ ] 권한별로 사이트 선택박스를 제공한다.
    - [ ] 현재 선택된 사이트에 등록된 메뉴가 트리로 출력된다.
    - [ ] 권한에 생성된 메뉴가 트리메뉴에 체크된다.
    - [ ] 권한별 생성 메뉴를 저장한다.
- 게시판
    - [ ] 게시판 목록을 조회한다.
        - [ ] 게시판의 게시물 조회버튼을 제공한다.
    - [ ] 게시판을 등록한다.
    - [ ] 게시판을 수정한다.
    - [ ] 게시판을 삭제한다.
- 게시물
    - [ ] 게시물 목록을 조회한다.
        - [ ] 공지게시물은 상단에 노출한다.
        - [ ] 다중선택하여 게시물을 삭제하거나 복원한다.
    - [ ] 게시물을 등록한다.
        - [ ] 게시판 설정에 따라 공지여부, 공개여부등의 입력항목을 제공한다.
        - [ ] 게시판 설정에 따라 업로드 입력항목을 제공한다.
        - [ ] 게시판 설정에 따라 웹에디터를 제공한다.
    - [ ] 게시물을 수정한다.
    - [ ] 게시물을 삭제한다.
- 묻고답하기
    - [ ] 묻고답하기 목록을 조회한다.
    - [ ] 질문을 등록한다.
    - [ ] 질문을 수정한다.
    - [ ] 질문을 삭제한다.
    - [ ] 답변을 등록한다.
        - [ ] 웹에디터를 제공한다.
    - [ ] 답변을 수정한다.
    - [ ] 답변을 삭제한다.
- 컨텐츠
    - [ ] 컨텐츠 목록을 조회한다.
    - [ ] 컨텐츠를 등록한다.
        - [ ] 웹에디터를 제공한다.
    - [ ] 컨텐츠를 수정한다.
        - [ ] 이전 컨텐츠 이력을 조회하고 복원할수 있다.
    - [ ] 컨텐츠를 삭제한다.
- 배너
    - [ ] 배너 목록을 조회한다.
    - [ ] 배너를 등록한다.
        - [ ] 배너유형을 입력항목으로 제공한다.
    - [ ] 배너를 수정한다.
    - [ ] 배너를 삭제한다.
- 첨부파일
    - [ ] 첨부파일 목록을 조회한다.
    - [ ] 첨부파일을 등록한다.
        - [ ] 첨부파일 유형을 선택입력항목으로 제공한다.(일반/링크)
    - [ ] 첨부파일을 수정한다.
    - [ ] 첨부파일을 삭제한다.
- 사이트 접속 로그
    - [ ] 사이트 접속시 로그를 기록한다.
- 메뉴 접속 로그
    - [ ] 메뉴 접속시 로그를 기록한다.
- 통계
    - [ ] 접속통계
        - [ ] 날짜별 접속통계
        - [ ] 메뉴별 접속통계
        - [ ] 브라우저별 접속통계
        - [ ] 유입경로별 접속통계
        - [ ] 기기별 접속통계
    - [ ] 다운로드 통계
        - [ ] 년별 다운로드 통계
        - [ ] 월별 다운로드 통계
        - [ ] 기간별 다운로드 통계
    - [ ] 묻고답하기 통계
        - [ ] 기간별 묻고답하기 통계
        - [ ] 답변자별 묻고답하기 통계
    - [ ] 로그인 기록 조회
- 메인 대시보드
    - [ ] 최근 접속자 현황(10일)을 그래프로 출력한다.
    - [ ] 최근 묻고답하기 현황(7일)을 그래프로 출력한다.
    - [ ] 최근 묻고답하기 분류별 현황을 그래프로 출력한다.
    - [ ] 최근 묻고답하기 목록 5건을 출력한다.
    - [ ] 최근 게시물 목록 5건을 출력한다. (게시판명 포함)
---

### User 기능 목록

- 메인화면

- 서브화면
