# Neo

## Database config

### H2  
```
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password: password
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console.enabled: true
```

### Postgres

Pulling postgres docker image
```
docker pull postgres:14.13-alpine3.20 
```

Running docker image
```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=<password> postgres:14.13-alpine3.20
```
Executing bash inside container
```
docker exec -it <container_name> bash
```
Signing as postgres user
```
psql -U postgres
```
Creating database
```
CREATE DATABASE neodb;
```
Creating 'positionRecord' table
```
CREATE TABLE positionRecord (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(255) NOT NULL,
    amount NUMERIC(19, 6),
    average_price NUMERIC(19, 2),
    target_price NUMERIC(19, 2),
    created_at TIMESTAMP
);

```
Configuration in application.yml file
```
  datasource:
    hikari:
      connection-timeout: 20000
      maximum-pool-size: 5
    url: jdbc:postgresql://localhost:5432/neodb
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: create-drop
```