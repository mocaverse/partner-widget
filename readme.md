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

### 4. Customizing Button Size

If you prefer a smaller-sized button, you can achieve this by adding a `size` attribute to the div element used in the previous step. Set the value of the `size` attribute to "compact" as follows:

```html
<div class="join-moca-button" size="compact" data-token="<%= jwt %>"></div>
```

#### JWT Information
The JWT should embed the fields below, and signed by your backend server using RS256 algorithm with an expiration of 1 hour:

- partnerId: Your **Partner ID** provided by us (e.g. `3d6cbe7d-4a27-4590-bb42-ada214da6cfb`)
- partnerUserId: Their unqiue **User ID** in your system (e.g. `2c55dc7d-5715-4cb9-9801-f62498c02077`)
- partnerUserName: Their **Display name** in your system (e.g. `gamertag123`)

### Integration Variables

| Attribute | Value      | Default  | Description                             |
|-----------|------------|----------|-----------------------------------------|
| `size`    | `normal` `compact` | `normal` | Optional. The size of the widget. |


You can customize the integration by using these variables and attributes as needed for your application.

## Example
See [sample-partner-app-express](sample-partner-app-express) of an example written in Express.js
