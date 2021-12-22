# Springboot-Rest-Jwt-Http-Only-Cookie-Authentication
Since Rest architecture is stateless, we need to authenticate incoming requests with JWT tokens. A good practice for a secure stateless application is keeping JWT tokens encrypted in Http Only Cookie.

## Author - Linkedin
[Zavlagas Nikos](https://www.linkedin.com/in/nikolaos-zavlagkas/)


## Usage

```<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--		Security Dependencies	-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!--  Jwt Dependencies   -->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.18.2</version>
        </dependency>

        <!--		Helping Dependencies	-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>31.0.1-jre</version>
        </dependency>
    </dependencies>
```

## Description
JWT tokens will be created in backend and stored in http only cookies. They will also be encrypted when responding to client.
When an authentication request is made to the server, we can get JWT tokens from cookies again. With this method, we do not have to store our tokens in browser storage, thus, It will be safer.

## License
[MIT](https://choosealicense.com/licenses/mit/)
