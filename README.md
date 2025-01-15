# 2025-Project-Team1-Winter-BE

---

## 개발 환경
### DB
```text
MySQL : 8.0.33
database : photo
username : root
password : 1234
```

## 테스트
1. 프론트엔드 서버를 3000포트로 활성화한다.
2. mysql 설정을 한다.
3. bash를 킨 뒤에 `git clone https://github.com/InQ-InQ-InQ-InQ-InQ/2025-Project-Team1-Winter-BE.git` 를 한다.
4. 디렉토리를 이동한다. `cd 2025-Project-Team1-Winter-BE`
5. git switch(checkout) 를 이용해 테스트를 진행할 branch를 선택한다. ex)`git switch develop`, `git switch release`
6. resources/application.yml 파일의 spring.jpa.hibernate.ddl-auto 속성 값을 `create`로 변경 한다.
7. `./gradlew bootRun` 을 이용해 8080포트를 활성화한다.
8. 프론트엔드를 이용해서 QA를 진행한다.