package com.account.yomankum.security.token;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.token.JwtValue;
import com.account.yomankum.security.oauth.type.TokenProp;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
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
        return Long.parseLong(claims.get(TokenProp.ID.name(), String.class));
    }

    public String getNickname(String token) {
        Claims claims = getClaims(token);
        return claims.get(TokenProp.NICKNAME.name(), String.class);
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get(TokenProp.ROLE.name(), String.class);
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
                .setSigningKeyResolver((SigningKeyResolver) publicKey)
                .build()
                .parseClaimsJws(idToken)
                .getBody();


    }
    public String getSnsUUID(JwtValue jwtValue, String token) {
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
        return snsClaims.get(TokenProp.SUB.name(), String.class);
    }

    private boolean isSignatureValid(PublicKey publicKey, String token) {
        String[] jwt = splitToken(token);


        try {
            Signature verifier = Signature.getInstance(TokenProp.ALGORITHM.getName());
            verifier.initVerify(publicKey);
            verifier.update((jwt[0]+"."+jwt[1]).getBytes(StandardCharsets.UTF_8));
            return verifier.verify(Base64.getUrlDecoder().decode(jwt[2]));
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("토큰을 파싱하다가 문제가 발생함.");
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
    }

    private PublicKey getPublicKey(String n, String e, String kty) {
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        PublicKey publicKey = null;

        try {
            KeyFactory factory = KeyFactory.getInstance(kty);
            publicKey = factory.generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            log.error("토큰을 파싱하다 문제가 발생함.");
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
        return publicKey;
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
        return token.split("\\.");
    }

    private Claims getClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
