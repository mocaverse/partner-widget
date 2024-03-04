## Adding 'Join Mocaverse' Button to your web app

This document describes how to add a **Join Mocaverse** button onto your web app.


## Set Up

Before integrating the button onto your site, we would provide the following to you:
- Partner ID
- A Private key for signing JWT

You would need to provide us with the following information:
- your domain(s) to whitelist, which your application will be embedding the button


## Integration Flow

With this integration, you will need to update your frontend and backend of your web application to render the button and appropriately pass information to Mocaverse.

### 1. Load the Mocaverse Platform Library
You must include the Mocaverse Platform Library on your web pages that integrate **Join Mocaverse** button.

```html
    <script src="https://static.mocaverse.xyz/js/partner.js" async defer></script>
```

### 2. Specify your Partner ID:
Specify the Partner ID with the mocaverse-partner-id meta element in the head of your web page.
```html
    <meta name="mocaverse-partner-id" content="<your-partner-id>"> 
```

### 3. Add a Join Mocaverse button:
Add a div element with the class join-moca-button to your page:
```html
    <div class="join-moca-button" data-token="<%= jwt %>"></div>
```

Your app must generate a JWT and embed it in this element on your website. In the above code snippet, replace `<%= jwt %>` with the JWT generated for your currently logged in user.

#### 3.1 JWT Information
The JWT should embed the fields below, and signed by your backend server using RS256 algorithm with an expiration of 1 hour:

- partnerId: Your **Partner ID** provided by us (e.g. `3d6cbe7d-4a27-4590-bb42-ada214da6cfb`)
- partnerUserId: Their unqiue **User ID** in your system (e.g. `2c55dc7d-5715-4cb9-9801-f62498c02077`)
- partnerUserName: Their **Display name** in your system (e.g. `gamertag123`)

#### 3.2 Dynamically loading the "Join Mocaverse" button
By default, the `Join Mocaverse` button loads when DOM is ready. Hence, If the button may not appear if your `Join Mocaverse` snippet is dynamically injected after DOM is ready. In this case, there are 2 options after injecting your `Join mocaverse` snippet:
1. dynamically load the mocaverse platform library in step 1, OR
2. call `mocaverse.partner.init();`

the above 2 options would initiate the rendering of the ` Join Nocaverse` button.

### 4. Add optional attributes

| Attribute | Value      | Default  | Description                             |
|-----------|------------|----------|-----------------------------------------|
| `redirect-url`  | URI | `normal` | Optional. Redirection URL after user joins mocaverse. |
| `size`    | `normal` `compact` | `normal` | Optional. The size of the widget. |

#### Redirect users back to your app after they've joined mocaverse
By default, users would continue their journey on Mocaverse after joining. If you would like users to come back to your app after joining mocaverse, add an absolute url for redirecting back to your site back specifying `redirect-url` attribute.

**Note**: The domain of the redirect url needs to be in the whitelisted domains defined during set up.

```html
<div class="join-moca-button" redirect-url="<%= your_url %>" data-token="<%= jwt %>"></div>
```

#### Customizing the button size

If you prefer a smaller-sized button, you can achieve this by adding a `size` attribute to the div element used in the previous step. Set the value of the `size` attribute to "compact" as follows:

```html
<div class="join-moca-button" size="compact" data-token="<%= jwt %>"></div>
```


### Example
See [sample-partner-app-express](sample-partner-app-express) of an example written in Express.js

## Related topics

### Constructing the URL manually
Although it is not the recommended method since url path and parameters are subject to changes, constructing the url manually for joining Mocaverse is possible. After constructing the jwt, you may include this information as well as an optional redirect url back to your app as follows:

```html
https://account.mocaverse.xyz/?partner=<jwt>&redirect=<url>
```

Note that the redirect url specified must be whitelisted in the "Set up" step.

### Getting Realm ID of a linked user
To obtain whether a user on your platform has already minted a Realm ID, you may call this Partner API to obtain this information.

```http
GET https://api.moca-id.mocaverse.xyz/api/partner/user
```

| Header | Type | Description |
| :--- | :--- | :--- |
| `partner-jwt` | `string` | **Required**. Your generated JWT, with partnerId and partnerUserId embedded. |

#### Response

```javascript
{
  "realmId" : string
}
```
The `realmId` attribute contains the user's Realm ID, or `null` in the case where the user or Realm ID is not found.

| Status Code | Description |
| :--- | :--- |
| 200 | OK |
| 400 | Bad request, missing or invalid parameters in the request |
| 401 | Unauthorized, missing partner-jwt |
| 403 | Forbidden, invalid partner-jwt or insufficient permissions |
| 429 | Rate Limit Exceeded |
| 500 | Internal server error or error in processing the request |
