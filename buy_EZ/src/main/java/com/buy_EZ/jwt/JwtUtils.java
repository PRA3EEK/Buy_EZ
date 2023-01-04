package com.buy_EZ.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.buy_EZ.securityConfig.CustomUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
      
	 private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	  @Value("${buy_EZ.jwtSecret}")
	  private String jwtSecret;
	  @Value("${buy_EZ.jwtExpirationMS}")
	  private int jwtExpirationMS;
	  @Value("${buy_EZ.jwtCookieName}")
	  private String jwtCookie;
	  
	  public String getJwtFromCookie(HttpServletRequest http)
	  {
		  Cookie cookie = WebUtils.getCookie(http, jwtCookie);
		  
		  if(cookie != null) return cookie.getValue();
		  
		  return null;
	  }
	  
	  public ResponseCookie generateJwtCookie(CustomUserDetails userDetails) {
		  
		  String jwt = generateJwtToken(userDetails.getUsername());
		  ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/buy_EZ").maxAge(24 * 60 * 60).httpOnly(true).build();
		  return cookie;
	  }
	  
	  public ResponseCookie getCleanJwtCookie()
	  {
		  ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/buy_EZ").build();
		  return cookie;
	  }
	  
	  public String getUsernameFromJwtToken(String token)
	  {
		  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	  }
	  
	  public boolean validateJwtToken(String authToken)
	  {
		  
		  try 
		  {
			 Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			 return true;
		  }
		  catch (SignatureException e) {
		      logger.error("Invalid JWT signature: {}", e.getMessage());
		    } catch (MalformedJwtException e) {
		      logger.error("Invalid JWT token: {}", e.getMessage());
		    } catch (ExpiredJwtException e) {
		      logger.error("JWT token is expired: {}", e.getMessage());
		    } catch (UnsupportedJwtException e) {
		      logger.error("JWT token is unsupported: {}", e.getMessage());
		    } catch (IllegalArgumentException e) {
		      logger.error("JWT claims string is empty: {}", e.getMessage());
		    }

		    return false;
		  
	  }
	  
	  public String generateJwtToken(String username)
	  {
		  
		  return Jwts.builder()
				  .setSubject(username)
				  .setIssuedAt(new Date())
				  .setExpiration(new Date(new Date().getTime() + jwtExpirationMS))
				  .signWith(SignatureAlgorithm.HS256, jwtSecret)
				  .compact();
		  
	  }
	  
//	  public String generateJwtToken(Authentication authentication)
//	  {
//		  CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
//		  
//		  return Jwts.builder()
//				  .setSubject(userDetails.getUsername())
//				  .setIssuedAt(new Date())
//				  .setExpiration(new Date((new Date()).getTime() + jwtExpirationMS))
//				  .signWith(SignatureAlgorithm.HS256, jwtSecret)
//				  .compact();
//	  }
	  
}
