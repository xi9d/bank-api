package com.paul.bankapi.Customer.Security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class TokenProvider {


    private final String secretKey="5586CA247556F2B567A9CA718083323EEEE7E1F82C03DD2961B5CB2B2BF21CB4";



    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    public <T> T extractClaims(String jwt, Function<Claims,T> claimsTFunction){
     final Claims claim = extractAllClaims(jwt);
     return claimsTFunction.apply(claim);
    }

    public Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
    public String extractUsername(String jwt){
        return extractClaims(jwt,Claims::getSubject);
    }

    // this method is used to validate if the token provided is valid or not
    public boolean isTokenValid(String jwt,UserDetails userDetails){
      final String username = extractUsername(jwt);
      return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }
    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaims(jwt, Claims::getExpiration);
    }


}
