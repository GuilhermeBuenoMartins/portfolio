[back](../3-Features.md)

### 3.8 Deactivate User

___
**Status**: Design

**Narrative**: As a user, I want to deactivate my account so that all users know I do not use it anymore.

**Business Rules**:
 1. User is the only one to be able to deactivate their account.
 2. Deactivated accounts cannot sign into the system.
 3. Deactivated accounts can be activated again. More details in [3.8.1 Reactivate User](8.1-Reactivate_User.md) 
 4. Deactivated accounts can be found by users.
 5. Users cannot schedule visits to real states with deactivated users.
 6. User should receive the message "Your account was deactivated successfully." when a account is deactivated.

**Technical Details**:
 1. User must be logged into the system to deactivate theirs account.
 2. The attribute `activated` must update to be `false` when user deactivates theirs account.
 3. The request to deactivate user account must be like:
 ```shell
curl --request PUT \
  --url 'http://localhost:8080/v1/users/deactivate
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw' \  
 ```
 4. Response must be like the following example:
 ```json
 {
    "timestamp": "2026-01-26T23:26:47.686966880Z",
    "status": "200 OK",
    "message": "Your account was deactivated successfully.",
    "data": {
        "cpf": "09853843013",
        "email": "a.username@domain.com",
        "fullName": "Complete name",
        "id": 1,
        "login": {
            "actived": false,
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

1. **Scenario:** User deactivates their account with a valid token
    **Given** I have a valid token
    **When** I deactivate my account with a valid token
    **Then** I receive the message "Your account was deactivated successfully."

2. **Scenario:** User tries to deactivate their account with an invalid token
    **Given** I have a valid token
    **When** I try to deactivate my account with an invalid token
    **Then** I receive the message "Token invalid. You must to sign in."