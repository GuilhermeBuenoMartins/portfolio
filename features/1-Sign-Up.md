[back](../3-Features.md)

### 3.1 Sign Up

___
**Status**: Complete

**Narrative**: As a user I want to sign up so that I can use the application.

**Business Rules**:
<ol>
 <li> Users must be able to sign up without prior authentication.
 <li> Any user who wishes to sign up must enter:
  <ul>
   <li> Username
   <li> Password
   <li> Recovery Password Question
   <li> Recovery Password Answer
   <li> Full Name
   <li> CPF
   <li> Email
   <li> List of Phones
   </ul>
 <li> Fields such as <b>CPF</b>, <b>Email</b>, and <b>Phones</b> must be validated.
 <li> The <b>Username</b> field must have: 
 <ul>
  <li> Minimum of 8 characters
  <li> No spaces
  <li> Maximum of 64 characters
 </ul>
 <li> The <b>Password</b> field must have:
 <ul>
  <li> Minimum of 8 characters
  <li> Minimum of 1 uppercase character <li> Minimum of 1 lowercase character
  <li> Minimum of 1 special character
  <li> Minimum of 1 digit
 </ul>
 <li> The <b>Recovery Password Question</b> field must have:
 <ul>
  <li> Minimum of 8 characters
  <li> Maximum of 256 characters
 </ul>
 <li> The <b>Recovery Password Answer</b> field must have:
 <ul>
  <li> Minimum of 8 characters
  <li> Maximum of 32 characters
 </ul>
 <li> The <b>Full Name</b> field must have:
 <ul>
  <li> Minimum of 8 characters
  <li> Maximum of 96 characters
 </ul>
 <li> The <b>CPF</b> field must have:
 <ul>
  <li> Exactly 11 digits
  <li> No special characters
 </ul>
 <li> The <b>Email</b> field must have:
 <ul>
  <li> Maximum of 64 characters
 </ul>
 <li> The <b>List of Phones</b> field must have:
 <ul>
  <li> Minimum of 1 phone number
  <li> Maximum of 2 phone numbers
  <li> Each phone must contain a minimum of 10 digits
  <li> Each phone must contain a maximum of 11 digits
 </ul>
</ol>

**Acceptance Criteria**:

<ol>
 <li><b>Scenario:</b> Sign up successfully
 <ul>
  <li><b>Given</b> I am not registered in the system
  <li><b>When</b> I sign up with my data
  <li><b>Then</b> I should receive the message "You were signed up successfully."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an existing Username
 <ul>
  <li><b>Given</b> I have an existing Username in the system
  <li><b>When</b> I try to sign up with an existing Username
  <li><b>Then</b> I should receive the message "Username was already  registered."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an existing CPF
 <ul>
  <li><b>Given</b> I have an existing CPF in the system
  <li><b>When</b> I try to sign up with an existing CPF
  <li><b>Then</b> I should receive the message "CPF was already  registered."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Username
 <ul>
  <li><b>Given</b> I have an invalid value in the field Username  <li><b>When</b> I try to sign up with an invalid Username
  <li><b>Then</b> I should receive the message "The field must have:  minimum of 8 characters; maximum of 64 characters; no spaces."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Password
 <ul>
  <li><b>Given</b> I have an invalid value in the field Password  <li><b>When</b> I try to sign up with an invalid Password
  <li><b>Then</b> I should receive the message "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Recovery Password Question 
 <ul>
  <li><b>Given</b> I have an invalid value in the field Recovery Password Question
  <li><b>When</b> I try to sign up with an invalid Recovery Password Question
  <li><b>Then</b> I should receive the message "The field must have: minimum of 8 characters; maximum of 256 characters."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Recovery Password Answer
 <ul>
  <li><b>Given</b> I have an invalid value in the field Recovery Password Answer
  <li><b>When</b> I try to sign up with an invalid Recovery Password Answer
  <li><b>Then</b> I should receive the message "The field must have: minimum of 8 characters; maximum of 32 characters."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Full Name
 <ul>
    <li><b>When</b> I try to sign up with an invalid Full Name
  <li><b>Then</b> I should receive the message "The field must have: minimum of 8 characters; maximum of 96 characters."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid CPF
 <ul>
  <li><b>Given</b> I have an invalid value in the field CPF
  <li><b>When</b> I try to sign up with an invalid CPF
  <li><b>Then</b> I should receive the message "The field must have: exactly 11 digits; no special characters."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid Email
  <ul>
  <li><b>Given</b> I have an invalid value in the field Email
    <li><b>When</b> I try to sign up with an invalid Email
  <li><b>Then</b> I should receive the message "The field must have a maximum of 64 characters."
 </ul>
 <li><b>Scenario:</b> Try to sign up using an invalid List of Phones
 <ul>
    <li><b>Given</b> I have an invalid value in the field Phones
    <li><b>When</b> I try to sign up with an invalid Phones
    <li><b>Then</b> I should receive the message "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits."
 </ul>
</ol>