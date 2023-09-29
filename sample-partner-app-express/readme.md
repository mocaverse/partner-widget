# Sample Partner App

The struture of the sample app is as follows:
- [views/index.ejs](views/index.ejs) - frontend page which includes the code snippets for adding the **Join Mocaverse** button
- [server.js](server.js) - web app with backend functions which generates a mocaverse partner JWT with hardcoded user name and id
- [example-partner-private.pem](example-partner-private.pem) - sample private key which will be provided to you during setup

## Running the example
1. Ensure node and npm are installed
2. Start the server
```
  npm i
  node server.js
```
3. Navigate to https://localhost:3002 in your browser

