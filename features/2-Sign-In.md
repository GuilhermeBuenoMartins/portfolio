[back](../3-Features.md)

### 3.2 Sign In

___
**Status**: Complete

**Narrative**: As a user I want to sign in so that I can use the application's features.

**Business Rules**:
<ol>
 <li> A user must enter a username and password;
 <li> If the username is null or empty, then application should display the message "Username cannot be null or empty."
 <li> If the password is null or empty, then application should display the message "Password cannot be null or empty."
 <li> When username or password is incorrect, the application should display the message "Username or password are incorrect."
 <li> When username and password are correct, the application should return a token.
</ol>

**Acceptance Criteria**: 
<ol>
 <li><b>Scenario:</b> Sign in with correct username and password
  <ul>
   <li><b>Given</b> I have a correct username and password
   <li><b>When</b> I sign in to the system
   <li><b>Then</b> I should receive a token
  </ul>
 <li><b>Scenario:</b> Try to sign in with empty username
  <ul>
   <li><b>Given</b> I do not have a username
   <li><b>When</b> I try to sign in
   <li><b>Then</b> I should receive the message "Username cannot be null or empty."
  </ul>
 <li><b>Scenario:</b> Try to sign in with empty password
  <ul>
   <li><b>Given</b> I do not have a password
   <li><b>When</b> I try to sign in
   <li><b>Then</b> I should receive the message "Password cannot be null or empty."
  </ul>
 <li><b>Scenario:</b> Try to sign in with incorrect username
  <ul>
   <li><b>Given</b> I do not have a password
   <li><b>When</b> I try to sign in
   <li><b>Then</b> I should receive the message "Passwrod cannot be null or empty."
  </ul>
 <li><b>Scenario:</b> Try to sign in with incorrect password
  <ul>
   <li><b>Given</b> I have an incorrect password
   <li><b>When</b> I try to sign in
   <li><b>Then</b> I should receive the message "Username or password are incorrect"
  </ul>
</ol>