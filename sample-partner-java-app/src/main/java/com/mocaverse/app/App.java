package com.mocaverse.app;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            RSAPrivateKey privateKey = App.getRSAPrivateKeyFromString(App.SAMPLE_PRIVATE_KEY);
            String jwt = generateJWT(privateKey);
            System.out.println("Generated JWT: " + jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateJWT(RSAPrivateKey privateKey) {
        Date NOW = new Date();
        Date ONE_HOUR_LATER = new Date(NOW.getTime() + 3600000);

        JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuedAt(NOW)
                .withExpiresAt(ONE_HOUR_LATER);

        jwtBuilder.withClaim("partnerId", "YOUR_PARTNER_ID")
                .withClaim("partnerUserId", "YOUR_PARTNER_USER_ID")
                .withClaim("partnerUserName", "YOUR_PARTNER_USER_NAME");

        return jwtBuilder.sign(Algorithm.RSA256(null, privateKey));
    }

    private static RSAPrivateKey getRSAPrivateKeyFromString(String privateKeyString) throws Exception {
        String privateKeyPEM = privateKeyString
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        return rsaPrivateKey;
    }

    static private String SAMPLE_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n" + //
            "MIIJQwIBADANBgkqhkiG9w0BAQEFAASCCS0wggkpAgEAAoICAQDNLdZ/5ldE1RCE\n" + //
            "aVgaacFQ2NaDFbM7nWiGqsJyIMH0yhs15RinWw/wLfJuwRYpw4ZkxmQ03vo4uXHT\n" + //
            "CCGQu3j8INrhIfd/x49Ork0WDYihrUFB8thbVlyIxd/y0yjjQigpb2qiaAvCR4N3\n" + //
            "nX8t3uZxvDJdvxzyTCBmpbJg2T4khG604BCfWsavkMhG3Qidi49gFJ7U+ZCU/L9G\n" + //
            "fsI/5Rtk21B+78zqHr6AntdlRtYdcMG8+yNqaMlEtI1vGtXfsYHz55ikjvPCeMZr\n" + //
            "Nv1Eh/CV9ibG4Nd3Rwi/39fr+aGCbrylFuqROmkNqppO0GvC83iF+VZczSwgR4w0\n" + //
            "v1fmbUg5+DZaWsURAMt0AMG5VUw80BSdPHTG0xcxlFeryv5dkrUbnP95+5HLNjQ6\n" + //
            "P0wtwJo/KwP1i9pybc44P3luodcK1DqWiSSVKu/fY8C4cNyyrOZS9rysfdFzh3M/\n" + //
            "6qF9JWNaQp2ZwgyQrtclHNH4VlWbdJm3uc/tgM+HBzzMFdJW2FACVGAkL7LdEwT6\n" + //
            "UzxfDZfTwyhBZ3jmc3sJMsyqyJymlXrq7WTRSZInh5nRNbgSpqUoEQa1zlgojScD\n" + //
            "xk3GUFSy4WBugG+zZ5KeniPlekIiakzRgKq+suOcsOJb90xwVDwRui/peKKg3HPH\n" + //
            "gq/RO/ykctOH+LOQuBJT2f7xyGO4MQIDAQABAoICAQC8JD8fstjtLi7iLDL0rWrt\n" + //
            "tpglRX2/uZxCeEyrGGyiCb2f0v6jX3Yi+A7pDjdDfp46xEzYympKH4pHR5p3u+C5\n" + //
            "2Wq7rEjmK9i1/lCDW0+haVyAm61XO77IUCIF6SkCEwT+0cLHP38g3DCqO4qGRnGr\n" + //
            "czYADqH9cF75oRELi8lW9PUK+LdlWHj+PlXX3RDsXi/Pm7j1dA7m/0hp3QxESNKw\n" + //
            "zgZnD38oY56uFHlQzf7CqkWNBAC6r3F3hdR3P/VEFOIkBIdMxiSxe8v+PkNJVxn+\n" + //
            "eOOf1Ebfggx83UaChLpb/y0OeRAIsbXpKTor8SQPdPBccYGGwUCoLNNc/uQcULM7\n" + //
            "iI90Arvf/gcEFb4aL9JAsU4oaGFBvpS2NC0YB4ipvFPsHGKE4jiYp/lGkrYxABKp\n" + //
            "rSbXAdb9Nd7emwjgVMUcOmMPKWmQhmf27Fe1gsdMNC0QLJ6ghBrqWbB4IkkjMZMj\n" + //
            "aNHJ6b9Rvxs0a6AOc//19u8+WgUHN3Yq4A7wUOs0y7DQBISlBDpm048+CWsIHmOy\n" + //
            "6NIrPAYs2ndDkdxo14tynvsh5AWU7m735anUyuf/DAVwP7dXpsmicg0JKEmXBL+w\n" + //
            "gPWldjxIiY6ZFtxXfdZB87Mj6BNu9isl46q1VR9QzHvfFcUIv3tuuQg6FqYMX3q7\n" + //
            "wjCXesD+rcEov7YlOFEnDQKCAQEA56rQUig4YPwH6f27jLx/K+fut38w+Bzr7OC8\n" + //
            "Wk1EGc37GNcA8MmJU1b4rMDG9jyBorCzCki4LOWeZHTIJxxaBB6Y4dcE+JSrxCdS\n" + //
            "5q/1onGJ9NSjOvEM0Wb9Vf3b7ltfyKR/aq6Zc5WSYmef3FIU7Ru53VG262+u9Eft\n" + //
            "ZMNS1aPfvntm1V64hA/50uERpJXurlxwdKaENrYM0BMxUgZ/pvlLtC4QeKCiWv0d\n" + //
            "BNdwrVCUGBVOVSIFIG+e0fXGwDFh1eyKkwJ6J5rqgETS4wHDAPlL3oGF/+swAPeY\n" + //
            "YoaTOgCmqcbapWQtK2cnsaAnSdPU4CazOnq4o8lPPVOw9zJtywKCAQEA4rrL3IXh\n" + //
            "0mR8JC788/1RCm4qnOSa09CTrwH0/PsY8sbCHXUMS8LUrBG2+dcSXcGzU5f+MgT8\n" + //
            "8F/bAMAgvrP0q950NIQr5Z/clnEjAZb2w9jLO4Of/tXTfoH/HgZWr0HtG+eVPfON\n" + //
            "ARZNigzs2XW2ff7zd0nXOjGB50R+qBStpkSRshZNM9JLv/eW5j2sHoYAlMFe4lAY\n" + //
            "NKqmoO2P0cpMDQERDEYht3CRE/t/etJ0tTvEX31UCga7pDvghUjOXR9UBh92Dlkx\n" + //
            "x1WlFwlgW/RUaZk3S/xRRrrGVFoLwfn3lT9NBCH932wb4CbHmb/xjqbFfXNg/bFf\n" + //
            "WHl33V59XIhycwKCAQBjfW8DqXQ06me3rJkL4rO2tl9FfKEaHMmRLB6XpVEWx+gL\n" + //
            "DeLAhbytBs+62PtZKjj+FmZ9hydZWK9CMwj2Cu35kO5VJtNise5mSgEHQw/5WEtH\n" + //
            "0r3bBURc9Z7cfC4kWidzcnmp5hvOkLCSBDCaHMIR5fNXQc2NpKP9Epq3yE5do4fm\n" + //
            "NPa/sXMKfBXs422BQkaDqP0+i3iCSoKMJYHr2RCfM7a4RYQbWdj/vG3m82LXe6Bu\n" + //
            "C1BdrUOIX4dUYfmd9lcu2OvWIJ03PwdwWJ48kTgexfE4OGXEt0shTzOpNGQr57Wr\n" + //
            "edPSbYgceKuKU94rjYaBUP1touCCrmSSUMvSDV0rAoIBADUUhEuhcGfgLIwQEBpT\n" + //
            "ENj/R6ypfHHgX+LV83QSmRmlXKam03UdLMsH7q15I4ydn40PhWxSbKEd39jCIihj\n" + //
            "WwGtt8jftKMylOMHo6lonWbmZ1YMj7WKe9QfPQnbdAndxlQFGJSDY1yAyzBprD2Q\n" + //
            "6GOA/6DKwb+W/ZGWIIzbInhNj0qBHBvovOE5ZrOKdrRCrPomdz9GfxQ9a3s7grMb\n" + //
            "yEg3GrWj6ZrXmK9z7rljcf1H+8ZbQgSC5QUjG4I4tE9q2TIBxZLAYP4I1/iXsl50\n" + //
            "yhpay25XqPOFII948U29ZrAjPLWd+6CF39TPX2VoYXYDJzMigu+Byg+diGlkCLdU\n" + //
            "Hm8CggEBAIXzrEIF7yxeguanW7lnB9VDinActZtEDsyROxEw36H+r0u2BrfHbFbj\n" + //
            "Qm6Tx0L13OQvTIH3phBn8sNJgheLItF4fUlQwtjJUjsknq+ITzVk6vFCxQHNeCjZ\n" + //
            "GrdH0ITwTPU2HsdfQwhlXKrpdzRWkozWRWjgudWqrfJtvr9XEufm0BzlAD5sQwpn\n" + //
            "q+ABjOihKdbi140lfVS1gpqQZMGkWJmLQw0R31FwGH0hnQcxqar3yBX5Jku/vqRz\n" + //
            "yDISH5h7m3UWsuqR/g8w/iKRwRaWuJRbXUOwDJVveUtZ9mk3d+6+FBoMRFqMVORP\n" + //
            "K/f92ArxYYibivPhsvAsWnKVL1YFlEE=\n" + //
            "-----END PRIVATE KEY-----";

}