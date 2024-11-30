package com.cognizant.assettracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	@Autowired
	private JwtHelper jwtHelper;


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		logger.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;
		String role= null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {

			token = requestHeader.substring(7);
			role = jwtHelper.getTokenRole(token);
			try {
				Claims claims= Jwts.parserBuilder()
						.setSigningKey(jwtHelper.secret)
						.build()
						.parseClaimsJws(token)
						.getBody();

				username = claims.getSubject();
				role =claims.get("role",String.class);


			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}


		} else {
			logger.info("Invalid Header Value !! ");
		}


		//
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


			//fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {

				//set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(role);
				SecurityContextHolder.getContext().setAuthentication(authentication);


			} else {
				logger.info("Validation fails !!");
			}


		}

		filterChain.doFilter(request, response);


	}
}
