package ar.com.colectiva.config.filters;

import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.colectiva.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    private final List<String> allowed = listOf(
        "/accounts/signup"
    );

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        AtomicBoolean toFilter = new AtomicBoolean(false);
        allowed.forEach(url -> {
            if(request.getRequestURI().contains(url)) {
                toFilter.set(true);
            }
        });
        if(toFilter.get()) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUserName(jwt);
            if (StringUtils.isNotEmpty(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails ud = userService.loadUserByUsername(username);
                if(jwtService.isTokenPartiallyValid(jwt, ud)) {
                    if(!request.getRequestURI().endsWith("/login/totp")){
                        var fail = new Fail("Usted está parcialmente autenticado. Debe ingresar el código TOTP en la URL /login/totp. ");
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(fail));
                        return;
                    }
                }
                if (jwtService.isTokenValid(jwt, ud)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            ud, null, ud.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                } else {
                    var fail = new Fail("El token recibido es inválido. Utilice otro. ");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(objectMapper.writeValueAsString(fail));
                    return;
                }
            }
        }
        catch(ExpiredJwtException e) {
            var fail = new Fail("El token recibido caducó. Utilice otro. ");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(fail));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
