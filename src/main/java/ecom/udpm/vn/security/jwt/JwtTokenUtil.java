package ecom.udpm.vn.security.jwt;

import ecom.udpm.vn.dto.response.Account.AccountResponse;
import ecom.udpm.vn.entity.Account;
import ecom.udpm.vn.service.impl.AccountService;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@Component
@Log4j2
@Getter
@CrossOrigin("*")
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    @Value("${app.jwtSecret}")
    private String SECRET_KEY;

    @Autowired
    AccountService accountService;

    @Autowired
    ModelMapper modelMapper;

    public String generateAccessToken(Account account) {

        return Jwts.builder()
                .setSubject(String.format("%s,%s", account.getId(), account.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userDetails", accountService.getAllDetails(account.getId()))
                .compact();

    }
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public AccountResponse getClaims(String token) {
        return modelMapper.map(parseClaims(token).get("userDetails"), AccountResponse.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}