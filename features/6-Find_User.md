[back](../3-Features.md)

### 3.6 Find User

___
**Status**: Design

**Narrative**: As a user, I want to find another user in the system so that I visualize the found user details.

**Business Rules**:

1. The user must be logged into the system to visualize a user detail.
2. The application must be able to find a user by the username.
3. The application must show the message if any user was not found.
4. The message for the user not found must be "User was not found in the system. Please, verify the username provided.".

**Technical Details**:

1. The application must authorize user's request through the authorization token.
2. The application must filter user by username.
3. Only one user must be returned when the filter is applied.
4. The request must be following the example:
```shell
curl --request GET \
  --url http://localhost:8080/v1/users/a.username \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw'
```
5. The application's response must be as following:
```json
{
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
```

**Acceptance Criteria**:

1. **Scenario:** User searches another existing user in the system by username
   + **Given** I have a valid token
   + **When** I search another existing user by username
   + **Then** I should receive details of the found user

2. **Scenario:** User searches a nonexisting user in the system by username
    + **Given** I have a valid token
    + **When** I search a nonexisting user by username
    + **Then** I should receive the message "User was not found in the system. Please, verify the username provided."