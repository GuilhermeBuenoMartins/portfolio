# visit-me

This backend project aims to provide a structure through which property owners can publish offers for sale or rent.
Interested parties can register to search for properties and schedule visits directly with the owners.
The buying or rental process is managed by the property owner, and prices can be negotiated.
Once a property is rented, it will be unavailable until the contract expires or the owner updates its status.
Properties that have been sold are permanently unavailable for purchase or rental.

## Content

[1. Setup](#1-setup)

[1.2. Prerequisites](#11-prerequisites)

[1.3. How to Execute](#12-how-to-execute)

[2. Documentation](#2-documentation)

[3. Features](#3-features)

[3.1 Sign Up](#31-sign-up)

[3.2 Sign In](#32-sign-in)

[3.3 Sign Out](#33-sign-out)

[3.4 Reset Password](#34-reset-password)

[3.5 List Users](#35-list-users)

[3.6 Find User](#36-find-user)

[3.7 Update User Data](#37-update-user-data)

[3.8 Deactivate User](#38-deactivate-user)

[3.9 Add Property for Sale or Rent](#39-add-property-for-sale-or-rent)

[3.10 List Properties](#310-list-properties)

[3.11 Update Property](#311-update-property)

[3.12 Create Visit](#312-create-visit)

[3.13 List Visits](#313-list-visits)

[3.14 Find Visit](#314-find-visit)

[3.15 Update Visit](#315-update-visit)

[3.16 Delete Visit](#316-delete-visit)

## 1. Setup

This section contents what tools you need to develop or execute this project in your local machine.

### 1.1. Prerequisites

**Visit-me** was made using the tools below.
For you execute successfully, use the same or newest versions.
You can use the following links to download them.
If one or more links does not work, you can find the tools search them.

 - [Git version 2.39.5](https://git-scm.com/install/linux)
 - [Java version 17.0.17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
 - [Apache Maven version 3.9.5](https://maven.apache.org/download.cgi)
 - [MySQL 8.0.43](https://dev.mysql.com/downloads/installer/)

Once installed all the tools above, you will be able to execute the project.

### 1.2 How to execute

## 2. Documentation

## 3. Features

The project's features were subdivided into **user stories** to detail the expected behaviors throughout its development.
A _user story_ is a desciption of a product feature from the user's perspective. 
And each user story in this project has:

 * a title ― a explicit name of feature.
 * a status ― a description of development phase such as **Definition**, **Design**, **Implementation**, **Testing**, or **Complete**.
 * a narrative ― a explanation with a struecture as "**As a... I want to... so that...**".
 * business rules ― a text or list of rules that describes the feature from business perspective.
 * acceptance criteria ― a set of scenarios about the feature behavior.

The following user stories do not have a specified reading order.
However, they are ordered by an implementation logic.
Hence, so that a better understand you should read them sequentially.

### 3.1 Sign Up

___
**Status**: Design

**Narrative**: As a user I want to sign up so that using the application.

**Business Rules**:
<ol>
 <li> User must sign up without any authentication.
 <li> Any user who wish sign up must enter: 
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
 <li> The <b>Username</b> field must have:
  <ul>
   <li> Minimum of 8 characters
   <li> None space
   <li> Maximum of 64 characters
  </ul>
 <li> The <b>Password</b> field must have:
  <ul>
   <li>Minimum of 8 characters
   <li>Minimum of 1 upper case character
   <li>Minimum of 1 lower case character
   <li>Minimum of 1 special character
   <li>Minium of 1 digit
  </ul>
 <li> The <b>Recovery Password Question</b> field must have:
  <ul>
   <li>Minimum of 8 characters
   <li>Maximum of 256 characters
  </ul>
 <li> The <b>Recovery Password Answer</b> field must have:
  <ul>
   <li>Minimum of 8 characters
   <li>Maximum of 32 characters
  </ul>
  <li> The <b>Full Name</b> field must have:
  <ul>
   <li>Minimum of 8 characters
   <li>Maximum of 96 characters
  </ul>
  <li> The <b>CPF</b> field must have:
  <ul>
   <li>Equals to 11 characters
   <li>None special characters
   <li>Valid
  </ul>
  <li> The <b>Email</b> field must have:
  <ul>
   <li>Maximum of 64 characters
   <li>Valid
  </ul>
  <li> The <b>List of Phones</b> field must have:
  <ul>
   <li>Minimum of 1 phone
   <li>Maximum of 2 phone
   <li>Each phone contains mininum of 10 digits
   <li>Each phone contains maximum of 11 digits
  </ul>
</ol>

**Acceptance Criteria**:

### 3.2 Sign In

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.3 Sign Out

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.4 Reset Password

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.5 List Users

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.6 Find User

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.7 Update User Data

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.8 Deactivate User

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.9 Add Property for Sale or Rent

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.10 List Properties

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.11 Update Property

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.12 Create Visit

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.13 List Visits

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.14 Find Visit

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.15 Update Visit

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:

### 3.16 Delete Visit

___
**Status**: Definition

**Narrative**:

**Business Rules**:

**Acceptance Criteria**:
