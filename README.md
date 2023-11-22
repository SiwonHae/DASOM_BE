

# 🤝 대학생 소개팅 서비스, 다솜! - BackEnd

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white"/>

## 🙋‍♂️ 우리 프로젝트를 소개합니다!
&nbsp;우리의 프로젝트 '다솜'은 대학생들을 대상으로 원하는 사람을 쉽게 만날 수 있도록 소개팅 서비스를 제공하는 웹 프로젝트입니다. 프로젝트 명인 '다솜'은 사랑을 뜻하는 순 우리말에서 비롯되었습니다.  
 &nbsp;설레는 마음으로 대학교에 오게 된 신입생, 휴학이나 학업 등의 이유로 학교에 친한 친구가 없는 학생들 중에서 새로운 사람을 만날 기회가 없거나, 내성적인 성격으로 친구를 쉽게 만들지 못하는 학생들이 있습니다. 우리의 프로젝트 다솜은 이러한 학생들을 대상으로 쉽게 만남을 할 수 있도록 도움을 주기 위해서 이 프로젝트를 기획하게 되었습니다.  
 > FrontEnd Repository : https://github.com/GHYoungKyun/DASOM_FE

## 💻 프로젝트 설치
* JDK 17이상이 설치되어 있어야 합니다.
* 대학생 메일 인증을 위해서 오픈소스 [UnivCert](https://github.com/in-seo/univcert)를 사용하였습니다.

### 1. Git Clone
```bash
$ git clone https://github.com/SiwonHae/DASOM_BE.git
```

### 2. 카카오, 네이버 소셜 로그인 설정, UnivCert API 키 발급
* [카카오 개발자](https://developers.kakao.com/)에서 애플리케이션 추가 -> Client Key와 Client Secret Key 발급, Redirect URL 설정
* [네이버 개발자](https://developers.naver.com/main/)에서 애플리케이션 등록 -> Client Key와 Client Secret Key 발급, Redirect URL 설정
* [메일 및 대학 인증 API(UnivCert)](https://univcert.com/)에서 API 키 발급

### 3. src/main/resources/application.yml 설정
```java
spring:  
  datasource:  
    driver-class-name: org.mariadb.jdbc.Driver  
    url: jdbc:mariadb://{DB 주소}/{DB명}  
    username: {DB Username}  
    password: {DB Password}  
  
  jpa:  
    hibernate:  
      ddl-auto: create // 배포 시에는 update 설정  
    properties:  
      hibernate:  
        format_sql: true  
        show_sql: true  
  
oauth:  
  kakao:  
    client-id: {KaKao Client Id}
    client-secret: {KaKao Cient Secret}  
    redirect-uri: {KaKao Redirect URL}  
    scope:  
      - name  
      - account_email  
      - gender  
      - birthday  
      - birthyear  
  
  naver:  
    client-id: {Naver Client Id}  
    client-secret: {Naver Client Secret}  
    redirect-uri: {Naver Redirect URL}  
    scope:  
      - name  
      - email  
      - birthyear  
      - birthday  
      - gender  
  
logging:  
  level:  
    org.hibernate:  
      type.descriptor.sql:  
  SQL: DEBUG  
  
myapp:  
  api_key: {UnivCert API Key}
```

### 4. 프로젝트 빌드 및 실행
```bash
$ ./gradlew build  
$ java -jar build/libs/{프로젝트명-버전}.jar
```

## 🪪 라이선스
&nbsp;This work is licensed under the MIT license.  
&nbsp;이 작업물은 MIT 라이선스에 따라 라이선스가 부여되어 있습니다.

## 🧑‍💻 프로젝트 멤버
|                                   BackEnd                                   |                                   BackEnd                                    |                                   FrontEnd                                   |                                   FrontEnd                                    |
| :--------------------------------------------------------------------------: | :---------------------------------------------------------------------------: | :--------------------------------------------------------------------------: | :--------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/62338444?v=4" width="100"> | <img src="https://avatars.githubusercontent.com/u/79629309?v=4" width="100"> | <img src="https://avatars.githubusercontent.com/u/84309081?v=4" width="100"> | <img src="https://avatars.githubusercontent.com/u/144300980?v=4" width="100"> |
|                    [임주혁](https://github.com/siwonhae)                     |                    [이아린](https://github.com/linavell)                     |                      [김영균](https://github.com/ghyoungkyun)                      |                   [권범준](https://github.com/goonbam0306)                 |
