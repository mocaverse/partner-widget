# Sample Partner App

## Running the example
1. Ensure java and maven are installed
2. Run the app to see generated JWT
```
  mvn compile exec:java -Dexec.mainClass=com.mocaverse.app.App
```
3. Replace the application with your actual key, partner Id, and user info in the code.


## Notes for Java implementation
1. If you were provided with private.key from us during set up, you may have to convert it to a pem format using `openssl pkcs8 -in private.key -topk8 -nocrypt -out private.pem`.

2. you may validate the generated JWT signed by the sample private key on https://jwt.io, by pasting the the following sample public key.

```
-----BEGIN PUBLIC KEY-----
MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAzS3Wf+ZXRNUQhGlYGmnB
UNjWgxWzO51ohqrCciDB9MobNeUYp1sP8C3ybsEWKcOGZMZkNN76OLlx0wghkLt4
/CDa4SH3f8ePTq5NFg2Ioa1BQfLYW1ZciMXf8tMo40IoKW9qomgLwkeDd51/Ld7m
cbwyXb8c8kwgZqWyYNk+JIRutOAQn1rGr5DIRt0InYuPYBSe1PmQlPy/Rn7CP+Ub
ZNtQfu/M6h6+gJ7XZUbWHXDBvPsjamjJRLSNbxrV37GB8+eYpI7zwnjGazb9RIfw
lfYmxuDXd0cIv9/X6/mhgm68pRbqkTppDaqaTtBrwvN4hflWXM0sIEeMNL9X5m1I
Ofg2WlrFEQDLdADBuVVMPNAUnTx0xtMXMZRXq8r+XZK1G5z/efuRyzY0Oj9MLcCa
PysD9Yvacm3OOD95bqHXCtQ6lokklSrv32PAuHDcsqzmUva8rH3Rc4dzP+qhfSVj
WkKdmcIMkK7XJRzR+FZVm3SZt7nP7YDPhwc8zBXSVthQAlRgJC+y3RME+lM8Xw2X
08MoQWd45nN7CTLMqsicppV66u1k0UmSJ4eZ0TW4EqalKBEGtc5YKI0nA8ZNxlBU
suFgboBvs2eSnp4j5XpCImpM0YCqvrLjnLDiW/dMcFQ8Ebov6XiioNxzx4Kv0Tv8
pHLTh/izkLgSU9n+8chjuDECAwEAAQ==
-----END PUBLIC KEY-----
```
