Disk sharing webapp
==========
Данный проект представляет урезанное REST API ресурса по обмену DVD-дисками. 



## Используемые технологии
* Java 8
* Spring Boot
* Spring Data (Hibernate)
* Spring Security
* Spring Web
* MySQL
* JsonWebToken

Для удобства просмотра ответов сервера можно использовать:
https://jsonformatter.curiousconcept.com/



## Запуск проекта
Перед запуской проекта необходимо выполнить SQL-скрипт src/main/resources/db/disk_sharing.sql, который создаст тестовую БД (disk_sharing) и заполнит данными.



## Аутентификация
Осуществляется по JWT токену. 

##### Пример запроса на получение токена (POST):
Адрес запроса:
```text
http://localhost:8080/api/get_token
```

Тело запроса:
```json
{"username": "mike.t@gmail.com", "password": "test"}
```

Заголовок запроса:
```
Content-Type: application/json
```

Нужный username взять из тестовой БД. У всех пользователей пароль одинаковый "test".

Настройки подключения к БД находятся в файле application.properties

В случае правильной комбинации username/password локальный сервер сгенерирует JWT-токен, который пришлет в ответе.

##### Пример ответа сервера:
```json
{"jwtToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlci5yQGdtYWlsLmNvbSIsImlhdCI6MTU5NTE3OTcyMywiZXhwIjoxNTk1MTc5NzgzfQ.GWF48yzF0roeuG5jh62MxHSiCiluCOMK0UccyP9q8Cw"}
```
По умолчанию время действия токена 10 минут (указывается в application.properties > jwt.validity).



## Как делать запросы

В запросах к эндпоинтам нужно добавить заголовок
```text
"Authorization" со значением: "JWT полученный_JWT_токен"
```

Пример заголовка:
```text
Authorization: JWT eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlci5yQGdtYWlsLmNvbSIsImlhdCI6MTU5NTE3OTcyMywiZXhwIjoxNTk1MTc5NzgzfQ.GWF48yzF0roeuG5jh62MxHSiCiluCOMK0UccyP9q8Cw
```



## Эндпоинты

* Список пользователей (GET)
```text
http://localhost:8080/api/users/
```

* Список собственных дисков у каждого пользователя (GET)
```text
http://localhost:8080/api/disks/own
```

* Список свободных дисков (GET) *1
```text
http://localhost:8080/api/disks/free
```

* Список дисков, взятых пользователем (GET) *2 
```text
http://localhost:8080/api/disks/taken

```

* Список дисков, взятых у пользователя (с указанием, кто взял) (GET) *3
```text
http://localhost:8080/api/disks/dropped
```

* Взять диск (POST) *4
```text
http://localhost:8080/api/disks/take/{diskId}
```

* Отдать диск (POST) *5
```text
http://localhost:8080/api/disks/return/{diskId}
```

##### Уточнения:
*1: Возвращает все не взятые диски у всех пользователей

*2: Возвращает диски взятые аутентифицированным пользователем

*3: Возвращает диски взятые у аутентифицированного пользователя

*4: Диск берется аутентифицированным пользователем

*5: Аутентифицированный пользователь возвращает диск владельцу



## Ограничения доменной модели

```text
-У диска только один хозяин
-Все диски в одном экземпляре
-В обмене могут участвовать только диски в собственности, т.е. нельязя взять у владельца, а потом дать другому участнику
-Нельзя взять диск, который уже взят
-Не владелец может вернуть только тот диск, который он брал
-Владелец может сам забрать свой диск, не дожидаясь возвращения
-Владелец не может взять диск у себя
-Владелец не может забрать диск у себя
```



## Что можно улучшить
```text
-Тесты must have
-Логирование
-Добавить ролевую модель (админ, юзер и т.д.)
-Рефреш токена
```
