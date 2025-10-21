# ms-user

This is the User service. The project builds with the Gradle wrapper and targets Java 21.

Quick start (Gradle wrapper)

Build

```bash
./gradlew clean build
```

Run

```bash
./gradlew bootRun
```

Tests

```bash
./gradlew test
```

Run with dev profile
--------------------

To run the application or tests using local dev credentials (kept out of VCS-sensitive places), start with the `dev` profile which reads `src/main/resources/application-dev.yml`:

```bash
./gradlew bootRun -Dspring.profiles.active=dev
```

Dev credentials (from `application-dev.yml`):

- user: `devuser` / `devpassword`
- admin: `admin` / `admin`


If you prefer Maven, a `pom.xml` is included for reference, but the repository's CI uses Gradle. It's best to standardize on one build system.

API examples

GET user details

```bash
curl -u user:password "http://localhost:8080/api/userdetails/1"
```

Update (PUT) user details

```bash
curl -X PUT -H "Content-Type: application/json" -u admin:admin \
  -d '{"title":"MR","firstName":"John","lastName":"Smith","gender":"M","address":{"street":"64 Margaret St","city":"Sydney","state":"NSW","postCode":"2000"}}' \
  "http://localhost:8080/api/userdetails/1"
```

Notes

- The project targets Java 21. The original README referenced JDK 11 / Maven 3.6.4 — update local tooling if you keep Maven.
- A Postman collection is available under `src/test/resources/postman`.

#Building the Project
The project is tested agaist JDK 11 and Maven 3.6.4
````
mvn clean install
````

#Run the project

```
mvn spring-boot:run
```

#Below are the curl commands to call the APIs

### GET user details
```
curl --location --request GET 'http://localhost:8080/api/userdetails/1' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Cookie: JSESSIONID=8D2C5BCA1756F0A1B95E0F2DE7F44407'

```
### update(PUT) user details
To only update specific fields, a K/V pair (Map) can be used.
The JPA Entity class is ready for that feature.(Not very sure whether it's required)
```
curl --location --request PUT 'http://localhost:8080/api/userdetails/1' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Cookie: JSESSIONID=8D2C5BCA1756F0A1B95E0F2DE7F44407' \
--data-raw '{"title":"MR","firstName":"John","lastName":"Smith","gender":"M","address":{"street":"64 Margaret St","city":"Sydney","state":"NSW","postCode":"2000"}}'
```

#Postman file is also available under test/resources/postman.
