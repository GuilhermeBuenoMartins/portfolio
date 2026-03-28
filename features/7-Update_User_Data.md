[back](../3-Features.md)

### 3.7 Update User Data

___
**Status**: Desig

**Narrative**: As a user, I want to update my data so that my data is reliable and up to date.

**Business Rules**: 

1. The application must authorize user's request through the authorization token.
2. The application must update data only if the user is the owner.
3. The application must allow the user to update any data, including password and recovery password answer.
4. The application must encode the password and recovery before saving them in the database.
5. When the user updates data, the application must display the message "Your data was updated successfully".
6. The validation data described in user story [1 Sign Up](1-Sign-Up.md) must be considered to update the data.

**Technical Details**:

1. The application must authorize user's request through the authorization token.
2. The application must identify the register to be updated by authorization token.
3. The password and recovery password must be encoded before to be saved in the database.
4. The request must follow the example:
```shell
curl --request PUT \
  --url http://localhost:8080/v1/users \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw' \
  --data '{
    "fullName": "Complete name",
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

5. The successful response must be like:
```bash
{
    "timestamp": "2026-01-26T23:26:47.686966880Z",
    "status": "200 OK",
    "message": "Your data was updated successfully.",
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

**Acceptance Criteria**:

1. **Scenario:** User updates registration with valid data
    + **Given** I have a valid token
    + **When** I update registration with valid data
    + **Then** I should receive the message "Your data was updated successfully."

2. **Scenario:** User tries to update username to an existing username.
    + **Given** I have a valid token
    + **When** I try to update username to an existing username
    + **Then** I should receive the message "Username was already registered."

3. **Scenario:** User tries to update username to an invalid username.
    + **Given** I have a valid token
    + **When** I try to update username to an invalid username
    + **Then** I should receive the message "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces."

4. **Scenario:** User tries to update password to an invalid password 
    + **Given** I have a valid token
    + **When** I try to update password to an invalid password
    + **Then** I should receive the message "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."

5. **Scenario:** User tries to update recovery password question to an invalid recovery password question
    + **Given** I have a valid token
    + **When** I try to update recovery password question to an invalid recovery password question
    + **Then** I should receive the message "The field must have: minimum of 8 characters; maximum of 256 characters."

6. **Scenario:** User tries to update recovery password answer to an invalid recovery password answer
    + **Given** I have a valid token
    + **When** I try to update recover password answer to an invalid recovery password answer
    + **Then** I should receive the message "The field must have: minimum of 8 characters; maximum of 32 characters."

7. **Scenario:** User tries to update full name to an invalid full name
    + **Given** I have a valid token
    + **When** I try to update full name to an invalid full name
    + **Then** I should receive the message "The field must have: minimum of 8 characters; maximum of 96 characters."

8. **Scenario:** User tries to update email to an invalid email
    + **Given** I have a valid token
    + **When** I try to update email to an invalid email
    + **Then** I should receive the message "The field must have a maximum of 64 characters."

9. **Scenario:** Users tries to update list of phones to an invalid list of phones
    + **Given** I have a valid token
    + **When** I try to update list of phones to an invalid list of phones
    + **Then** I should receive the message "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits."