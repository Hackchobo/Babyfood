package com.green.babyfood.config.security;

import com.green.babyfood.config.RedisService;
import com.green.babyfood.config.security.model.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {
    public final Key ACCESS_KEY;
    public final Key REFRESH_KEY;
    public final String TOKEN_TYPE;

    public final long ACCESS_TOKEN_VALID_MS = 3_600_000L; // 1000L * 60 * 60 -> 1시간
    public final long REFRESH_TOKEN_VALID_MS = 1_296_000_000L; // 1000L * 60 * 60 * 24 * 15 -> 15일

    private final RedisService redisService;

    @Autowired
    public JwtTokenProvider(@Value("${springboot.jwt.access-secret}") String accessSecretKey
            , @Value("${springboot.jwt.refresh-secret}") String refreshSecretKey
            , @Value("${springboot.jwt.token-type}") String tokenType
            , RedisService redisService) {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretKey);
        this.ACCESS_KEY = Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        this.REFRESH_KEY = Keys.hmacShaKeyFor(refreshKeyBytes);
        this.TOKEN_TYPE = tokenType;
        this.redisService = redisService;
    }


    public String generateJwtToken(String strIuser, List<String> roles, long token_valid_ms, Key key) {
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 시작");
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(createClaims(strIuser, roles))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + token_valid_ms))
                .signWith(key)
                .compact();
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 완료");
        return token;
    }

    private Claims createClaims(String strIuser, List<String> roles) {
        Claims claims = Jwts.claims();
        claims.setSubject(strIuser);
        claims.put("roles", roles);
        return claims;

        //return (Claims)Jwts.claims().setSubject(strIuser).put("roles", roles);
    }

    public Authentication getAuthentication(String token) {
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 시작");
        //UserDetails userDetails = SERVICE.loadUserByUsername(getUsername(token));
        UserDetails userDetails = getUserDetailsFromToken(token, ACCESS_KEY);
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 완료, UserDetails UserName : {}"
                , userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token, Key key) {
        try {
            Claims claims = getClaims(token, key);
            String strIuser = claims.getSubject();
            List<String> roles = (List<String>) claims.get("roles");
            return MyUserDetails
                    .builder()
                    .iuser(Long.valueOf(strIuser))
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String resolveToken(HttpServletRequest req, String type) {
        log.info("JwtTokenProvider - resolveToken: HTTP 헤더에서 Token 값 추출");
//        for(Enumeration names = req.getHeaderNames(); names.hasMoreElements();) {
//            String name = (String)names.nextElement();
//            for(Enumeration values = req.getHeaders(name); values.hasMoreElements();) {
//                String value = (String)values.nextElement();
//                log.info("{}: {}", name, value);
//            }
//        }
        String headerAuth = req.getHeader("authorization");
//        return headerAuth == null ? null : headerAuth.substring(type.length()).trim();
        return headerAuth != null && headerAuth.startsWith(String.format("%s ", type)) ? headerAuth.substring(type.length()).trim() : null; // Bearer_!!!!! 문자열을 자르는 역활을 한다.
    }

    public Claims getClaims(String token, Key key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getTokenExpirationTime(String token, Key key) {
        try {
            return getClaims(token, key).getExpiration().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
    // Filter에서 사용
    public boolean isValidateToken(String token, Key key) {
        log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 시작");
        try {
            return !getClaims(token, key).getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 예외 발생");
            return false;
        }
        // 지났으면 true > false;
        // 안 지났으면 false > true;
    }
}
