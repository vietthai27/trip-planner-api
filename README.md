# Trip Planner API

Spring Boot REST API with:
- MySQL
- JWT authentication
- Role-based access: ADMIN, USER, MODDER
- Multi-language messages: English and Vietnamese
- Pure REST API

## Run MySQL

```bash
docker run --name trip-mysql -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=trip_planner -p 3306:3306 -d mysql:8.4
```

## Run app

```bash
mvn spring-boot:run
```

## Auth APIs

Register:

```http
POST /api/auth/register
Content-Type: application/json
Accept-Language: vi
```

```json
{
  "username": "thai",
  "password": "123456",
  "role": "USER"
}
```

Login:

```http
POST /api/auth/login
```

```json
{
  "username": "thai",
  "password": "123456"
}
```

Use token:

```http
Authorization: Bearer <token>
```

## Protected APIs

- `GET /api/user/profile` allows USER, MODDER, ADMIN
- `GET /api/modder/review` allows MODDER, ADMIN
- `GET /api/admin/dashboard` allows ADMIN
