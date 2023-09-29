const express = require('express');
const fs = require('fs');
const path = require('path');
const app = express();
const port = 3002;

app.set('view engine', 'ejs');
app.set('index', path.join(__dirname, 'views'));

app.get('/', (req, res) => {
  // EXAMPLE ONLY - REPLACE WITH YOUR Partner ID and DATA OF YOUR CURRENTLY LOGGED IN USER
  const token = generateMocaverseJWT({
    partnerId: '3f29be20-8eab-42b9-92e9-43b145956ab6',
    partnerUserId: '9999',
    partnerUserName: 'gamertag123',
  });

  res.render('index', { jwt: token });
});

function generateMocaverseJWT(data) {
  const jwt = require('jsonwebtoken');

  // EXAMPLE KEY BELOW - REPLACE WITH PRIVATE KEY GIVEN DURING SETUP
  const partnerPrivateKey = fs.readFileSync(
    'example-partner-private.pem',
    'utf8',
  );

  const token = jwt.sign(data, partnerPrivateKey, {
    expiresIn: '1h',
    algorithm: 'RS256',
  });

  return token;
}

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
