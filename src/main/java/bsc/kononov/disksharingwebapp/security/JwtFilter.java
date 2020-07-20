package bsc.kononov.disksharingwebapp.security;

import bsc.kononov.disksharingwebapp.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt фильтр: проверяет наличие токена и его валидность
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtManager jwtManager;

    @Autowired
    public JwtFilter(UserService userService, JwtManager jwtManager) {
        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String token = jwtManager.getTokenFromRequestHeader(authHeader);
        String username = null;

        try {
            if (!token.isEmpty())
                username = jwtManager.getUsernameFromToken(token);
            else
                throw new IllegalArgumentException();

        } catch (SignatureException e) {
            System.out.println("Wrong token");
        } catch (MalformedJwtException e) {
            System.out.println("Token is incorrect format");
        } catch (IllegalArgumentException e) {
            System.out.println("Token not present");
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtManager.validateJwtToken(userDetails, token)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
