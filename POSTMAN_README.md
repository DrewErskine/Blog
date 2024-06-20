PeepPea Blog API Documentation

  -  This document provides the necessary information to perform API operations using Postman or curl commands for the PeepPea Blog application.

**Base URL**
http://localhost:8080

**Authorization**
Most endpoints require a Bearer token for authorization. Ensure you replace YOUR_JWT_TOKEN with your actual JWT token.

**Get All Users**
  -  Request Type: GET
  -  Endpoint: /USERS?page=0&size=10
  -  Headers:
  Authorization: Bearer YOUR_JWT_TOKEN
  Accept: application/json
  -  Parameters:
  page: 0
  size: 10

curl Command:

```curl -X GET "http://localhost:8080/USERS?page=0&size=10" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Accept: application/json"```

---

**Get User by ID**
  -  Request Type: GET
  -  Endpoint: /USERS/{id}
  -  Headers:
  Authorization: Bearer YOUR_JWT_TOKEN
  Accept: application/json

  -  curl Command:

```url -X GET "http://localhost:8080/USERS/{id}" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Accept: application/json"```

---

**Create User**
Request Type: POST
Endpoint: /USERS
Headers:
  Authorization: Bearer YOUR_JWT_TOKEN
  Content-Type: application/json
Body (JSON):
{
  "username": "drew",
  "password": "password",
  "email": "example@email.com",
  "role": "USER",
  "lastOnline": "2024-01-01T00:00:00",
  "profilePictureUrl": "imissher.png"
}

curl Command:

```curl -X POST "http://localhost:8080/USERS" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Content-Type: application/json" -d '{...}'```

---

**Update User**
  -  Request Type: PUT
  -  Endpoint: /USERS/{id}
  -  Headers:
  Authorization: Bearer YOUR_JWT_TOKEN
  Content-Type: application/json
  -  Body (JSON):
  {
    "username": "peep",
    "password": "saraEnjoyer3",
    "email": "example@email.com",
    "role": "USER",
    "lastOnline": "2024-07-15T00:00:00",
    "profilePictureUrl": "miss/her.png"
  }

  -  curl Command:

```curl -X PUT "http://localhost:8080/USERS/{id}" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Content-Type: application/json" -d '{...}'```

---

**Delete User**
  -  Request Type: DELETE
  -  Endpoint: /USERS/{id}
  -  Headers:
  Authorization: Bearer YOUR_JWT_TOKEN

  -  curl Command:

```curl -X DELETE "http://localhost:8080/USERS/{id}" -H "Authorization: Bearer YOUR_JWT_TOKEN"```

---

**Login**
  -  Request Type: POST
  -  Endpoint: /login
  -  Headers:
  Content-Type: application/x-www-form-urlencoded
  -  Body (urlencoded):
  username: drew
  password: password
  
  -  curl Command:

```curl -X POST "http://localhost:8080/login" -H "Content-Type: application/x-www-form-urlencoded" -d "username=drew&password=password"```
