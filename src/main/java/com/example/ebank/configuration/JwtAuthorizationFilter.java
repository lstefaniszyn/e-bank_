package com.example.ebank.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String IDENTITY_KEY = "identity_key";

	private final String secret;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			String secret) {
		super(authenticationManager);
		this.secret = secret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			filterChain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String header = request.getHeader(TOKEN_HEADER);
		if (!Strings.isBlank(header) && header.startsWith(TOKEN_PREFIX)) {
			String token = header.substring(TOKEN_PREFIX.length());
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(secret)
					.build()
					.parseClaimsJws(token);
			String identityKey = jws.getBody().get(IDENTITY_KEY, String.class);
			return new UsernamePasswordAuthenticationToken(identityKey, null, List.of());
		}
		return null;
	}
}
