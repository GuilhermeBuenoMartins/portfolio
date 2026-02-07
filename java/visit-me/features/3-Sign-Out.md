[back](../3-Features.md)

### 3.3 Sign Out

___
**Status**: Design

**Narrative**: As a user I want to sign out so that my last tokens won't be used by anyone.

**Business Rules**:
<ol>
  <li> The system must validate token before sign out.
  <li> When user signed out successfuly, the system should display the message "You are signed out from system.".
  <li> When user tries to sign out with a invalid token, the system should display "Token invalid. You must to sign in."
</ol>

**Technical Details**:

1. The `invalid_tokens_tb` is a table to store invalid tokens after a successful request.

2. The `invalid_tokens_tb` contains only one column called `token` as a primary key.

3. Request to sign out must be as in the following example.

```shell
curl --request POST \
  --url http://localhost:8080/v1/home/sign-out \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw'
```

4. The response must be as in the example below in successful case.

```json
{
	"timestamp": "2026-01-28T17:28:26.091098800Z",
	"status": "200 OK",
	"message": "You are signed out from system.",
	"data": null
}
```

5. In failed cases, the response must be as in the following example.

```json
{
	"timestamp": "2026-01-28T17:26:54.123759200Z",
	"status": "401 UNAUTHORIZED",
	"path": "/v1/home/sign-out",
	"messages": [
		{
			"cause": "Header \"Authorization\".",
			"message": "Token invalid. You must to sign in."
		}
	]
}
```

**Acceptance Criteria**:
<ol>
 <li><b>Scenario:</b> User sign out with a valid token
 <ul>
  <li><b>Given</b> I have a valid token
  <li><b>When</b> I sign out from the system
  <li><b>Then</b> I should receive the message "You are signed out from system."
 </ul>
 <li><b>Scenario: User sign out with an invalid token</b>
 <ul>
  <li><b>Given</b> I have an invalid token
  <li><b>When</b> I sign out from the system
  <li><b>Then</b> I should receive the message "Token invalid. You must to sign in."
 </ul>
</ol>
