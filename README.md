# API CONTROLLERS
## User Controller:

### Get all users:
```http
GET /api/users
```
Example response:
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com"
  }
]
```
##
### Get user by id:
```http
GET /api/users/{id}
```
Example response:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com"
}
```
##
### Add new user:
```http
POST /api/users
```
Request body:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```
Example response:
```json
{
  "id": 3,
  "name": "John Doe",
  "email": "john@example.com"
}
```
##
### Login user:
```http
POST /api/users/login
```
Request body:
```json
{
    "email": "fatih@gmail.com",
    "password": "fatih1234"
}
```
Example response:
```json
{
    "id": 3,
    "name": "Fatih Yıldırım",
    "email": "fatih@gmail.com",
    "password": "fatih1234"
}
```
##
### Update user:
```http
PUT /api/users/{id}
```
Request body:
```json
{
  "name": "Johnathan Doe",
  "email": "johnathan@example.com",
  "password": "newpassword123"
}
```
response:
```
200 OK
```
##
### Delete user:
```http
DELETE /api/users/{id}
```
Response:
```
200 OK
```
## Restaurant Controller:
### Get all restaurants:
```http
GET /api/restaurants
```
Example response:
```json
[
  {
    "id": 1,
    "name": "Gourmet Paradise",
    "address": "123 Food St"
  },
  {
    "id": 2,
    "name": "Bistro Bliss",
    "address": "456 Culinary Ave"
  }
]
```
##
### Get restaurant by ID
```http
GET /api/restaurants/{id}
```
Example response:
```json
{
  "id": 1,
  "name": "Gourmet Paradise",
  "address": "123 Food St"
}
```
##
### Add new restaurant
```http
POST /api/restaurants
```
Request body:
```json
{
  "name": "Gourmet Paradise",
  "address": "123 Food St",
  "tables": [
    { "name": "Table 1", "available": true },
    { "name": "Table 2", "available": true }
  ]
}
```
Example response:
```json
{
  "id": 1,
  "name": "Gourmet Paradise",
  "address": "123 Food St",
  "tables": [
    { "id": 1, "name": "Table 1", "available": true },
    { "id": 2, "name": "Table 2", "available": true }
  ]
}

```
##
### Update restaurant
```http
PUT /api/restaurants/{id}
```
Request body:
```json
{
  "name": "Gourmet Heaven",
  "address": "456 Culinary Ave",
  "tables": [
    { "id": 1, "name": "Table 1", "available": true },
    { "id": 2, "name": "Table 2", "available": false }
  ]
}
```
Example response:
```
200 OK
```
##
### Upload restaurant photo
```http
POST /api/restaurants/{id}/photo
```
Request body:
| Key | Value | Description |
| :- | :- | :- |
| `file` | `upload file` | optional |

Example response:
```
200 OK
```
##
### Get restaurant photo
```http
GET /api/restaurants/{restaurantId}/photo/{photoId}
```
Example response:

![image](https://github.com/user-attachments/assets/14dbba79-79d8-42e8-8ff4-ac64fb37c4a9)
##
### Delete restaurant
```http
DELETE /api/restaurants/{id}
```
Response:
```
200 OK
```
## Reservation Controller:
### Getl all reservations
```http
GET /api/reservations
```
Example response:
```json
[
    {
        "id": 13,
        "restaurant": {
            "id": 1,
            "name": "Gourmet Paradise",
            "address": "123 Food St",
            "tables": [
                {
                    "id": 3,
                    "name": "Table A3",
                    "available": false
                },
                {
                    "id": 1,
                    "name": "Table A1",
                    "available": false
                },
                {
                    "id": 2,
                    "name": "Table A2",
                    "available": true
                }
            ]
        },
        "user": {
            "id": 1,
            "name": "Fatih Yıldırım",
            "email": "fatih@gmail.com",
            "password": "fatih1234"
        },
        "table": {
            "id": 3,
            "name": "Table A3",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    },
    {
        "id": 14,
        "restaurant": {
            "id": 1,
            "name": "Gourmet Paradise",
            "address": "123 Food St",
            "tables": [
                {
                    "id": 3,
                    "name": "Table A3",
                    "available": false
                },
                {
                    "id": 1,
                    "name": "Table A1",
                    "available": false
                },
                {
                    "id": 2,
                    "name": "Table A2",
                    "available": true
                }
            ]
        },
        "user": {
            "id": 3,
            "name": "Fatma Yıldırım",
            "email": "fatma@gmail.com",
            "password": "fatma1234"
        },
        "table": {
            "id": 1,
            "name": "Table A1",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    },
    {
        "id": 15,
        "restaurant": {
            "id": 2,
            "name": "Bistro Bliss",
            "address": "456 Culinary Ave",
            "tables": [
                {
                    "id": 6,
                    "name": "Table B3",
                    "available": false
                },
                {
                    "id": 4,
                    "name": "Table B1",
                    "available": true
                },
                {
                    "id": 5,
                    "name": "Table B2",
                    "available": true
                }
            ]
        },
        "user": {
            "id": 3,
            "name": "Fatma Yıldırım",
            "email": "fatma@gmail.com",
            "password": "fatma1234"
        },
        "table": {
            "id": 6,
            "name": "Table B3",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    }
]
```
##
### Get reservation by ID
````http
GET /api/reservations/{id}
````
Example response:
````json
{
    "id": 13,
    "restaurant": {
        "id": 1,
        "name": "Gourmet Paradise",
        "address": "123 Food St",
        "tables": [
            {
                "id": 3,
                "name": "Table A3",
                "available": false
            },
            {
                "id": 1,
                "name": "Table A1",
                "available": false
            },
            {
                "id": 2,
                "name": "Table A2",
                "available": true
            }
        ]
    },
    "user": {
        "id": 1,
        "name": "Fatih Yıldırım",
        "email": "fatih@gmail.com",
        "password": "fatih1234"
    },
    "table": {
        "id": 3,
        "name": "Table A3",
        "available": false
    },
    "reservationTime": "2024-12-01T19:00:00"
}
````
##
### Add new reservation
````http
POST /api/reservations
````
Request body:
````json
{
  "restaurantId": 1,
  "userId": 1,
  "reservationTime": "2024-12-01T19:00:00"
}
````
Example response:
````json
{
    "id": 16,
    "restaurant": {
        "id": 2,
        "name": "Bistro Bliss",
        "address": "456 Culinary Ave",
        "tables": [
            {
                "id": 4,
                "name": "Table B1",
                "available": false
            },
            {
                "id": 6,
                "name": "Table B3",
                "available": false
            },
            {
                "id": 5,
                "name": "Table B2",
                "available": true
            }
        ]
    },
    "user": {
        "id": 4,
        "name": "Efe Yıldırım",
        "email": "efe@gmail.com",
        "password": "efe1234"
    },
    "table": {
        "id": 4,
        "name": "Table B1",
        "available": false
    },
    "reservationTime": "2024-12-01T19:00:00"
}
````
##
### Get reservation by user
````http
GET /api/reservations/user/{userId}
````
Example response:
````json
[
    {
        "id": 13,
        "restaurant": {
            "id": 1,
            "name": "Gourmet Paradise",
            "address": "123 Food St",
            "tables": [
                {
                    "id": 3,
                    "name": "Table A3",
                    "available": false
                },
                {
                    "id": 2,
                    "name": "Table A2",
                    "available": true
                },
                {
                    "id": 1,
                    "name": "Table A1",
                    "available": false
                }
            ]
        },
        "user": {
            "id": 1,
            "name": "Fatih Yıldırım",
            "email": "fatih@gmail.com",
            "password": "fatih1234"
        },
        "table": {
            "id": 3,
            "name": "Table A3",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    }
]
````
##
### Get reservation by restaurant
````http
GET /api/reservations/restaurant/{restaurantId}
````
Example response:
````json
[
    {
        "id": 13,
        "restaurant": {
            "id": 1,
            "name": "Gourmet Paradise",
            "address": "123 Food St",
            "tables": [
                {
                    "id": 3,
                    "name": "Table A3",
                    "available": false
                },
                {
                    "id": 2,
                    "name": "Table A2",
                    "available": true
                },
                {
                    "id": 1,
                    "name": "Table A1",
                    "available": false
                }
            ]
        },
        "user": {
            "id": 1,
            "name": "Fatih Yıldırım",
            "email": "fatih@gmail.com",
            "password": "fatih1234"
        },
        "table": {
            "id": 3,
            "name": "Table A3",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    },
    {
        "id": 14,
        "restaurant": {
            "id": 1,
            "name": "Gourmet Paradise",
            "address": "123 Food St",
            "tables": [
                {
                    "id": 3,
                    "name": "Table A3",
                    "available": false
                },
                {
                    "id": 2,
                    "name": "Table A2",
                    "available": true
                },
                {
                    "id": 1,
                    "name": "Table A1",
                    "available": false
                }
            ]
        },
        "user": {
            "id": 3,
            "name": "Fatma Yıldırım",
            "email": "fatma@gmail.com",
            "password": "fatma1234"
        },
        "table": {
            "id": 1,
            "name": "Table A1",
            "available": false
        },
        "reservationTime": "2024-12-01T19:00:00"
    }
]
````
##
### Delete reservation
````http
DELETE /api/reservations/{id}
````
Response:
````
200 OK
````
