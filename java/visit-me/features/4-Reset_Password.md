[back](../3-Features.md)

### 3.4 Reset Password

___
**Status**: Design

**Narrative**: As a user I want to reset my password so that I can authenticate me and change my password.

**Business Rules**:
1. No token or authorization should be used in requests to obtain recovery password questions or provide recovery password answers.

2. The application must return the recovery password question when the user provides a registered CPF. 

3. If the provided CPF is not registered, the application should show the message "This CPF does not exist in the system. Please, sign up."

4. However, if the user provides an invalid CPF, the application should display the same message of the [3.1 Sign Up](1-Sign-Up.md), "The field must have: exactly 11 digits; no special characters."

4. When the application returns the recovery password question, the user can answer it with the new password.

5. The new password must follow the same rules specified in the user story [3.1. Sign Up](1-Sign-Up.md), including the error message.

6. If the user answers the recovery password question incorrectly, the application should display the message "Incorrect answer! You are unauthorized to change password.".

7. However, if the user answers the recovery password question correctly, the application should update the password and display the message "Your password was changed successfully! Please, sign in to system with the new password."

**Technical Details**:

1. A method GET to obtain a recovery password question at endpoint `/v1/home/recovery/{cpf}`. For example:

```shell
curl --request GET \
--url 'http://localhost:8080/v1/home/recovery/09853843013
```

2. The expected the status code is `200` and response should be similar to the one below.

```json
{
	"timestamp": "2026-01-28T17:28:26.091098800Z",
	"status": "200 OK",
	"message": null,
	"data": "What is the question?"
}
```

3. If a failure occurs to find a recovery password question by CPF, the response should be status code `404` and body as:

```json
{
	"timestamp": "2026-01-28T17:26:54.123759200Z",
	"status": "404 NOT_FOUND",
	"path": "/v1/home/recovery/{cpf}",
	"messages": [
		{
			"cause": "Path \"cpf\".",
			"message": "This CPF does not exist in the system. Please, sign up."
		}
	]
}
```

4. To send a recovery password answer, a request must be done at endpoint `/v1/home/recovery/{cpf}` with a method POST as following example.

```shell
curl --request POST \
--url 'http://localhost:8080/v1/home/recovery/09853843013' \
--data '{
    "recoveryPasswordAnswer": "This is the question",
    "password": "n3wPa$$w0rd"
}'
```

5. The expected the status code is `200` and following response body for successful request:

```json
{
	"timestamp": "2026-01-28T17:28:26.091098800Z",
	"status": "200 OK",
	"message": "Your password was changed successfully! Please, sign in to system with the new password.",
	"data": {
        "username": "a.username",
        "password": "$2a$10$S9sRgfKCRkHisiFP3ExYWO0jLxkTDFc5mbqsSINT8dUUDQCIFKFby",
        "recoveryPasswordQuestion": "What is the question?",
        "recoveryPasswordAnswer": "$2a$10$xDvSie2S3YBetthHlsR7b.hDSVM7MVgu1qGmAKIE6yzTkbJullHqi"
    }
}
```

6. However, if an incorrect recovery password answer is provided, the application should return a status code `401` and reponse:

```json
{
	"timestamp": "2026-01-28T17:26:54.123759200Z",
	"status": "401 UNAUTHORIZED",
	"path": "/v1/home/recovery/{cpf}",
	"messages": [
		{
			"cause": "Field \"recoveryPasswordAnswer\".",
			"message": "Incorrect answer! You are unauthorized to change password."
		}
	]
}
```

7. The validations about the invalid values provided in requests also generate error messages in the response as the example:

```json
{
	"timestamp": "2026-01-28T17:26:54.123759200Z",
	"status": "400 BAD_REQUEST",
	"path": "/v1/home/recovery/{cpf}",
	"messages": [
        {
			"cause": "Path \"cpf\".",
			"message": "The field must have: exactly 11 digits; no special characters."
		},
        {
			"cause": "Field \"password\".",
			"message": "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."
		},
	]
}
```

**Acceptance Criteria**:
1. **Scenario:** The user obtains the recovery password question successfully.
   + **Given** a registered CPF in the system
   + **When** I uses the CPF to obtain recovery password question
   + **Then** I should receive the recovery password question
   
3. **Scenario:** The user uses an unregistered CPF to obtain recovery password question.
   + **Given** I have an unregistered CPF in the system
   + **When** I uses the CPF to obtain recovery password question
   + **Then** I should receive the message "This CPF does not exist in the system. Please, sign up."

4. **Scenario:** The user uses an invalid CPF to obtain recovery password question.
   + **Given** I have an invalid CPF in the system
   + **When** I uses the CPF to obtain recovery password question
   + **Then** I should receive the message "The field must have: exactly 11 digits; no special characters."

5. **Scenario:** The user changes the password successfully.
   + **Given** I have a recovery password question
   + **When** I answer the recovery password question correctly
   + **Then** I should receive the message "Your password was changed successfully! Please, sign in to system with the new password."

6. **Scenario:** The user tries change the password to an invalid password
   + **Given** I have a recovery password question
   + **When** I answer the recovery password question correctly with an invalid password
   + **Then** I should receive the message "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."

7. **Scenario:** The user uses an incorrect answer to change the password.
   + **Given** I have a recovery password question
   + **When** I answer the recovery password question incorrectly
   + **Then** I should receive the message "Incorrect answer! You are unauthorized to change password."