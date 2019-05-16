# Game of Chance
A RESTful service which players can play a simple game of chance. 
Player can win either coins or a free next round. Each player has infinite credit.
 Every round costs 10 coins and has 30% chance of winning 20 coins and 10% chance 
 of winning free next round.

## How to Run

```
git clone https://github.com/erdalzeynep/codeassignment.git
cd codeassignment
./mvnw clean install -DskipTests
./mvnw spring-boot:run 
```

### To Play

```
./play.sh <user-name>
```

### To Get User Detail

```
./get-user.sh <user-name>
```

### To Get Round Detail

```
./get-round.sh <round-id>
```

#### Running the tests
```
./mvnw test
```
### Technologies That is Used

* [Spring Boot](https://spring.io/projects/spring-boot)

* [Hibernate with Embeded Database H2](https://hibernate.org/orm/documentation/5.4/)

* [Maven](https://maven.apache.org/)

