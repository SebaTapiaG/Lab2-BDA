package proyecto.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Repository;
import proyecto.entities.ClienteEntity;
import proyecto.entities.RepartidorEntity;

import javax.crypto.SecretKey;
import java.util.Date;

@Repository
public class JwtMiddlewareService{

    private final SecretKey secretKey;

    public JwtMiddlewareService(SecretKey secretKey){
        this.secretKey = secretKey;
    }

    public String generateToken(ClienteEntity clienteEntity){
        Date expiration_date = new Date(System.currentTimeMillis() + 86400000);
        return Jwts
                .builder()
                .claim("user_id", clienteEntity.getId_cliente())
                .claim("name", clienteEntity.getNombre())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration_date)
                .signWith(secretKey)
                .compact();
    }

    public String generateTokenRepartidor(RepartidorEntity repartidorEntity){
        Date expiration_date = new Date(System.currentTimeMillis() + 86400000);
        return Jwts
                .builder()
                .claim("user_id", repartidorEntity.getId_repartidor())
                .claim("name", repartidorEntity.getNombre())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration_date)
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token){
        try{
            Jws<Claims> jws = Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return !jws.getPayload().getExpiration().before(new Date());
        }catch(ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch(MalformedJwtException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ClienteEntity decodeJWT(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long id = claims.get("user_id", Long.class);
        String username = claims.get("name", String.class);

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId_cliente(id.intValue());
        clienteEntity.setNombre(username);
        return clienteEntity;
    }
}
