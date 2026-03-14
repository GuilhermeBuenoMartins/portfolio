[back](../3-Features.md)

### 3.5 List Users

___
**Status**: Design

**Narrative**: As user I want to list users so that I see everybody is in the system.

**Business Rules**:
 
1. The user must be logged in the system to list all users in the system.
2. The user can define the number of users by page.
3. The user can filter by one user's full name.
4. The system must show 5 users by page when user does not define it.
5. The system only displays full name, username and e-mail for each user in the list.  

**Technical Details**:

1. The application must validation the user request through authorization token.
2. The application can filter user's full name that contains a name.
3. Request to list all user must be like:
```shell
curl --request GET \
  --url http://localhost:8080/v1/users?page=1&size=5&fullname=name \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw'
```
4. Response application must be the following JSON:
```json
{
    "totalItems": 6
    "users": [...]
    "totalPages": 2
    "currentPage": 1
}
```

**Acceptance Criteria**:

 1. **Scenario:** User lists all users  
    + **Given** I have a valid token
    + **When** I request a list all users
    + **Then** I should receive a list of 5 users by page

2. **Scenario:** User lists all users with 3 user by page  
    + **Given** I have a valid token
    + **When** I request a list all users with 3 user by page
    + **Then** I should receive a list of 3 users by page

3. **Scenario:** User lists all users with name "John"
    + **Given** I have a valid token
    + **When** I request a list all with name "John"
    + **Then** I should receive a list of users that contains name "John"