[back](../2-Documentation.md)

### 2.1. Sign up

While the application is running, you can sign up in the system and your data will be stored in the database.
The **cURL** command below is a request to the application.
You can use it to test the endpoint or create a new registration. (See more about the business rules [here](../features/1-Sign-Up.md)

```shell
curl --request POST \
  --url http://localhost:8080/v1/home/sign-up \
  --header 'Content-Type: application/json' \
  --data '{
    "fullName": "Complete name",
    "cpf": "09853843013",
    "email": "a.username@domain.com",
    "login": {
        "username": "a.username",
        "password": "aP*ssw0rd",
        "recoveryPasswordQuestion": "What is the question?",
        "recoveryPasswordAnswer": "This is the question"
    },
    "phones": [
        { "phone": "11964530987" },
        { "phone": "1142789801"}
    ]
}'
```

You should get a reponse similar to the one below from the application.
If your response is different from the expected, please read the **user story** [3.1. Sign-up](../features/1-Sign-Up.md) for more information.

```json
{
    "timestamp": "2026-01-26T23:26:47.686966880Z",
    "status": "201 CREATED",
    "message": "You were signed up successfully.",
    "data": {
        "cpf": "09853843013",
        "email": "a.username@domain.com",
        "fullName": "Complete name",
        "id": 1,
        "login": {
            "actived": true,
            "id": 1,
            "password": "$2a$10$S9sRgfKCRkHisiFP3ExYWO0jLxkTDFc5mbqsSINT8dUUDQCIFKFby",
            "recoveryPasswordAnswer": "$2a$10$xDvSie2S3YBetthHlsR7b.hDSVM7MVgu1qGmAKIE6yzTkbJullHqi",
            "recoveryPasswordQuestion": "What is the question?",
            "username": "a.username"
        },
        "phones": [
            {
                "id": 1,
                "phone": "11964530987"
            },
            {
                "id": 2,
                "phone": "1142789801"
            }
        ]
    }
}
```