
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
