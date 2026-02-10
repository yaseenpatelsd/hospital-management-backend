package hospital_management_sytem.Jwt;

import hospital_management_sytem.Exeption.JwtExceptions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private final long expire=24*60*60*1000;

    public String generateToken(String username){
       return Jwts.builder().setSubject(username)
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    public String extractUsernameFromToken(String token){
     return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean isTokenvalid(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }catch (JwtExceptions e){
            throw new JwtExceptions("Jwt Token Expire");
        }catch (Exception e){
            return false;
        }
    }

}
