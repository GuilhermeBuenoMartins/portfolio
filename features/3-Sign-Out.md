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

**Acceptance Criteria**:
<ol>
 <li><b>Scenario:</b> User sign out with a valid token
 <ul>
  <li><b>Given</b> I have a valid token
  <li><b>When</b> I sign out from the system
  <li><b>Then</b> I should receive the message "You are signed out from system."
 </ul>
 <li><b>Scenario:</b>
 <ul>
  <li><b>Given</b> I have an invalid token
  <li><b>When</b> I sign out from the system
  <li><b>Then</b> I should receive the message "Token invalid. You must to sign in."
 </ul>
</ol>