package fr.dawan.training.interceptors;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.SecurityConfig;
import fr.dawan.training.dto.LogMsg;
import fr.dawan.training.dto.LogMsg.LogLevel;
import fr.dawan.training.dto.LogMsg.LogType;
import fr.dawan.training.tools.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// System.out.println(request.getMethod() + " : " + request.getRequestURI());
		if (!request.getMethod().equals("OPTIONS")) {// do not intercept OPTIONS Request
			if (!Arrays.asList(SecurityConfig.authorizedUrls).contains(request.getRequestURI())) {
				String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

				if (authHeader == null || (!authHeader.startsWith("Bearer") && !authHeader.startsWith("bearer")))
					throw new ServletException("Invalid authorization"); // prévoir une gestion de l'exception =>

				String jwtToken = authHeader.substring(7); // retirer le mot bearer du token
				String username = null;
				try {
					username = jwtUtils.extractUsername(jwtToken);
					if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						if (jwtUtils.validateToken(jwtToken, userDetails)) {
							UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(authToken);
						} else
							throw new ServletException("Invalid token");

					} else
						throw new ServletException("Invalid authentication");
				} catch (ExpiredJwtException | ServletException ex) {
					//ex.printStackTrace();
					LogMsg m = new LogMsg();
					m.setCode(401);
					m.setLevel(LogLevel.ERROR);
					m.setType(LogType.ACCESS);
					m.setMessage(ex.getMessage());
					m.setPath(request.getRequestURI());
					response.getWriter().write(objectMapper.writeValueAsString(m));
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
