package com.mafer.CarRegisty.filter;

import com.mafer.CarRegisty.service.impl.JwtService;
import com.mafer.CarRegisty.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(StringUtils.isEmpty(authHeader)){
            filterChain.doFilter(request,response);
            return; //este if lo que hace es que en caso de que no venga la cabecera, devuelve la llamada tal cual la envía
        }
        jwt=authHeader.substring(7); //Por defecto las cabeceras jwt empiezan en el subindice 7, por tanto recupera
        //de ahí en adelante y lo guarda en jwt
        log.info("JWT -> {}",jwt);
        userEmail = jwtService.extractUserName(jwt);
        if(!StringUtils.isEmpty(userEmail)&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.userDetailService().loadUserByUsername(userEmail); //ESTO LO HE CAMBIADO YO ANTES: userService.userDetailsService().loadUserByUsurname
            if(jwtService.isTokenValid(jwt,userDetails)){
                log.info("User - {}", userDetails);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);

            }
        }
        filterChain.doFilter(request,response);

    }
}
