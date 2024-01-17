package com.account.yomankum.security.jwt;

import com.account.yomankum.security.domain.JwtValue;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@PropertySource("classpath:application.yml")
public class TokenParser {
    private final JwtParser jwtParser;
    public TokenParser(@Value("${token.secret.key}") String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().setSigningKey(key).build();
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        if (claims.getSubject() != null) {
            return !claims.getExpiration().before(new Date());
        }
        return false;
    }

    public Long getId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.get("id", String.class));
    }

    public String getNickname(String token) {
        Claims claims = getClaims(token);
        return claims.get("nickname", String.class);
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    public String getSnsTokenSecret(String token, String where, String what) {
        String[] jwt = splitToken(token);

        int whereTokenBody = getWhereTokenBody(where);

        //시그니쳐는 byte[] 배열로 받아와야함..
        if (whereTokenBody == 2) {
            byte[] signatureBytes = Base64.getDecoder().decode(jwt[2]);
            return new String(signatureBytes, StandardCharsets.UTF_8);
        }

        String tokenPiece = jwt[whereTokenBody];
        byte[] tokenBytes = Base64.getDecoder().decode(tokenPiece);

        String tokenPieceValueByJson = new String(tokenBytes, StandardCharsets.UTF_8);
        JsonObject headerJson = new JsonParser()
                .parse(tokenPieceValueByJson)
                .getAsJsonObject();

        return headerJson.get(what).getAsString();
    }

    private Claims getSnsClaims(String idToken, PublicKey publicKey) {
        return Jwts.parser()
                .setSigningKeyResolver(new SigningKeyResolverAdapter() {
                    @Override
                    public Key resolveSigningKey(JwsHeader header, Claims claims) {
                        return publicKey;
                    }
                })
                .build()
                .parseClaimsJws(idToken)
                .getBody();


    }
    public String getSnsUUID(JwtValue jwtValue, String token) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        String n = jwtValue.getN();
        String e = jwtValue.getE();
        String kty = jwtValue.getKty();
        String alg = jwtValue.getAlg();

        PublicKey publicKey = getPublicKey(n, e, kty);
        boolean signatureValid = isSignatureValid(publicKey, token);

        if (!signatureValid) {
            throw new RuntimeException();
        }

        Claims snsClaims = getSnsClaims(token, publicKey);
        return snsClaims.get("sub", String.class);
    }

    private boolean isSignatureValid(PublicKey publicKey, String token) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String[] jwt = splitToken(token);

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update((jwt[0]+"."+jwt[1]).getBytes(StandardCharsets.UTF_8));

        return verifier.verify(Base64.getUrlDecoder().decode(jwt[2]));

    }

    private PublicKey getPublicKey(String n, String e, String kty) throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory factory = KeyFactory.getInstance(kty);
        return factory.generatePublic(spec);


    }

    private int getWhereTokenBody(String where) {
        int whereTokenBody = 0;
        if (where.equals("header")) {
            whereTokenBody = 0;
        }
        if (where.equals("payload")) {
            whereTokenBody = 1;
        }
        if (where.equals("signature")) {
            whereTokenBody = 2;
        }
        return whereTokenBody;
    }

    private String[] splitToken(String token) {
        String[] jwt = token.split("\\.");
        return jwt;
    }

    private Claims getClaims(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims;
    }
}
