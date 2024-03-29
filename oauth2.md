# OAuth 2.0 Integration

This document provides the steps for partners to integrate their applications to Mocaverse using OAuth 2.0 (Authorization Code Flow).

- [Set up](#set-up)
- [Obtaining User Access Token](#obtaining-user-access-token)
- [OAuth2 APIs](#oauth2-apis)
  - [User Registration](#1-user-registration)
  - [Check User Registraiont](#2-check-user-registration)
  - [Get Sales Eligibility](#3-user-sales-eligibility)

## Set up

- Obtain the `client_id` and `client_secret` from our team
- Provide `redirect_uri` values to our team (e.g. https://your-app.example.com/callback)

## Obtaining User Access Token

To obtain a user's access token, the OAuth2 flow would be:
1. [User Authorization Request](#1-user-authorization-request): Construct a link for the user authorization through Mocaverse.
2. [Authorization code request callback](#2-authorization-code-request-callback): User is redirected back to your app with an authorization code after the authorization is complete.
3. [Access Token Request](#3-access-token-request): Your app requests an access token using the authorization code.

#### Error Handling
The following fields are common for errors that could happen while obtaining the user access token.

| Field             | Description                                 |
|-------------------|---------------------------------------------|
| error             | [Error Code Reference](#oauth2-error-codes) |
| error_description | Error details/summary                       |

Example:
```json
{
  "error": "invalid_request",
  "error_description": "invalid client_id"
}
```

### 1. User Authorization Request

Construct the URL for users to log into Mocaverse.
#### Request
```http
GET https://oauth.mocaverse.xyz/api/oauth/auth
```

Content-type: `application/x-www-form-urlencoded`

| Parameter     | Description                                                   |
|---------------|---------------------------------------------------------------|
| response_type | must be set to `code`                                         |
| client_id     | your `client_id` as provided during [Set up](#set-up)         |
| redirect_uri  | One of the registered `redirect_uri` from  [Set up](#set-up)  |
| state         | a random string generated by your app to prevent CSRF attacks |

##### Example

```html
<a href="https://oauth.mocaverse.xyz/api/oauth/auth?response_type=code&client_id=EXAMPLE_CLIENT_ID&redirect_uri=https%3A%2F%2Fyour-app.example.com%2Fcallback&state=RANDOM_STRING_v6glrJn3gf3qL4rPFLBB">
  Login with Mocaverse
</a>
```

The above example contains a Mocaverse login link with the following parameters
- response_type=code
- client_id=EXAMPLE_CLIENT_ID
- redirect_uri=https://your-app.example.com/callback
- state=RANDOM_STRING_v6glrJn3gf3qL4rPFLBB

#### Response

| Response | Description                                                                                                                 |
|----------|-----------------------------------------------------------------------------------------------------------------------------|
| 302      | **Valid** `redirect_uri` and `client_id`. The user will be redirected to log in and authorize your app on Mocaverse's site. |
| 400      | **Invalid** `redirect_uri` or `client_id`.                                                                                  |

### 2. Authorization code request callback

If an authenticated user **approves** the authorization request, they will be redirected back to your `redirect_uri` containing a one-time use authorization `code` and the provided `state` from [step 1](#1-user-authorization-request).

| Field | Description                                                                                                                                                                           |
|-------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| code  | The authorization code for the next step in the OAuth2 flow                                                                                                                           |
| state | The `state` for your app to prevent CSRF attacks. Validating the `state` if the value matches with the initial request during the User Authorization Request is strongly recommended. |

#### Examples
**Success**
```
https://your-app.example.com/callback?code=602cbbe1-60dd-482f-b0d8-329f5f1254c7&state=RANDOM_STRING_v6glrJn3gf3qL4rPFLBB
```

**Error**

If the user **denies** the authorization request or if there is an error with your request, the user will be redirected to your `redirect_uri` with `error` and the provided `state` from  [step 1](#1-user-authorization-request).

```
https://your-app.example.com/callback?error=access_denied&state=E0BXkRkKnvqMiDdYC8MW
```

### 3. Access Token Request

#### Request

```http
POST https://oauth.mocaverse.xyz/api/oauth/token
```

| Header       | Description                         |
|--------------|-------------------------------------|
| Content-Type | `application/x-www-form-urlencoded` |

| Parameter     | Description                                                                           |
|---------------|---------------------------------------------------------------------------------------|
| grant_type    | must be `authorization_code`                                                          |
| client_id     | your `client_id` as provided during [Set up](#set-up)                                 |
| client_secret | your `client_secret` as provided during [Set up](#set-up)                             |
| code          | the authorization code received from [step 2](#2-authorization-code-request-callback) |
| redirect_uri  | must match the value used during [step 1](#1-user-authorization-request)              |

##### Example

```http
POST https://oauth.mocaverse.xyz/api/oauth/token
```

**Request Body**

```
grant_type=authorization_code&client_id=EXAMPLE_CLIENT_ID&client_secret=MOCA_SECRET&code=602cbbe1-60dd-482f-b0d8-329f5f1254c7&redirect_uri=https%3A%2F%2Fyour-app.example.com%2Fcallback
```

The above example contains a request with the following parameters
- grant_type=authorization_code
- client_id=EXAMPLE_CLIENT_ID
- client secret=MOCA_SECRET
- code=602cbbe1-60dd-482f-b0d8-329f5f1254c7
- redirect_uri=https://your-app.example.com/callback

#### Response

| Field        | Description                                |
|--------------|--------------------------------------------|
| access_token | the access token to be used our OAuth APIs |
| token_type   | Type of token                              |

##### Example

```json
{
   "access_token": "AEjHL4Petp2EbF7GMlEQsUd5KYRVPKfOCogEzg-mHryAT",
   "token_type": "bearer"
}
```

* * *

## OAuth2 APIs
This section lists the APIs available for access tokens obtained from our OAuth2 flow.

### 1. User Registration

Link a user id of your platform to Mocaverse.

#### Request

```
POST https://api.moca-id.mocaverse.xyz/api/oauth/partner/user
```

| Header        | Description             |
|---------------|-------------------------|
| Authorization | `Bearer <access_token>` |
| Content-Type  | `application/json`      |

| Parameter       | Description                                                                       |
|-----------------|-----------------------------------------------------------------------------------|
| partnerUserId   | Their unique User ID in your system (e.g. `2c55dc7d-5715-4cb9-9801-f62498c02077`) |
| partnerUserName | (optional) Their Display name in your system (e.g. `gamertag123`)                 |

##### Example

```json
{
  "partnerUserId": "2c55dc7d-5715-4cb9-9801-f62498c02077",
  "partnerUserName": "gamertag123"
}
```

#### Response

| Response     | Description       |
|--------------|-------------------|
| existingUser | Mocaverse User ID |

##### Examples
**Success**

```json
{
  "existingUser": "f1925118-2b20-435c-994c-9f7e5fb812c4"
}
```

**Fail**

```json
{
  "error": "invalid_param",
  "message": "A different user ID has been linked to your Moca ID."
}
```

### 2. Check User Registration

Check if the current user has linked your platform's user id.

#### Request

```
GET https://api.moca-id.mocaverse.xyz/api/oauth/partner/user
```

| Header        | Description             |
|---------------|-------------------------|
| Authorization | Bearer `<access_token>` |

#### Response

| Response      | Description                            |
|---------------|----------------------------------------|
| partnerUserId | your user id if linked, null otherwise |

##### Examples
**User has linked**
```json
{ "partnerUserId": "123456" }
```
**User has not linked**
```json
{ "partnerUserId": null }
```

### 3. User Sales Eligibility

* * *

Getting sales eligibility info of a user.

#### Request

```
GET https://api.moca-id.mocaverse.xyz/api/oauth/partner/user-eligibility
```

| Header        | Description             |
|---------------|-------------------------|
| Authorization | Bearer `<access_token>` |

#### Response

| Response    | Description                  |
|-------------|------------------------------|
| realmId     | Realm ID of the current user |
| guaranteed  | Guaranteed allocation in USD |
| waitlisted  | Waitlisted allocation in USD |
| description | Eligibility text description |

##### Example

```json
{
  "realmId": "user.moca",
  "guaranteed": 4200,
  "waitlisted": 300,
  "description": "You qualify for guaranteed token allocation of up to USD4200, and an additional waitlisted allocation up to USD300 through a raffle if there's under allocation. Refer to the token sale rules for details."
}
```

* * *

### References

#### Oauth2 Error Codes

**Reference:** [rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)

```
access_denied
    The resource owner or authorization server denied the
    request.

invalid_request
    The request is missing a required parameter, includes an
    unsupported parameter value (other than grant type),
    repeats a parameter, includes multiple credentials,
    utilizes more than one mechanism for authenticating the
    client, or is otherwise malformed.

invalid_client
    Client authentication failed (e.g., unknown client, no
    client authentication included, or unsupported
    authentication method).  The authorization server MAY
    return an HTTP 401 (Unauthorized) status code to indicate
    which HTTP authentication schemes are supported.  If the
    client attempted to authenticate via the "Authorization"
    request header field, the authorization server MUST
    respond with an HTTP 401 (Unauthorized) status code and
    include the "WWW-Authenticate" response header field
    matching the authentication scheme used by the client.

invalid_grant
    The provided authorization grant (e.g., authorization
    code, resource owner credentials) or refresh token is
    invalid, expired, revoked, does not match the redirection
    URI used in the authorization request, or was issued to
    another client.

unauthorized_client
    The authenticated client is not authorized to use this
    authorization grant type.

unsupported_grant_type
    The authorization grant type is not supported by the
    authorization server.

unsupported_response_type
    The authorization server does not support obtaining an
    authorization code using this method.

invalid_scope
    The requested scope is invalid, unknown, malformed, or
    exceeds the scope granted by the resource owner.

server_error
    The authorization server encountered an unexpected
    condition that prevented it from fulfilling the request.
    (This error code is needed because a 500 Internal Server
    Error HTTP status code cannot be returned to the client
    via an HTTP redirect.)

temporarily_unavailable
    The authorization server is currently unable to handle
    the request due to a temporary overloading or maintenance
    of the server.  (This error code is needed because a 503
    Service Unavailable HTTP status code cannot be returned
    to the client via an HTTP redirect.)
```

#### OAuth2 API Unauthorized Response: 401

```
{
  "error": "client_error",
  "message": "invalid signature" | "invalid token" | "No auth token"
}
```

| message         | Description                               |
|-------------------|-------------------------------------------|
| invalid signature | Signature of the auth token is invalid    |
| invalid token     | Auth token is malformed                   |
| No auth token     | `Authorization: Bearer` header is missing |
