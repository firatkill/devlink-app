package com.worksOnLocal.DevLink.security.jwt;

import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=extractTokenFromRequest(request);

        if(token!=null){
            String username=jwtService.extractUsername(token);

           User user= userRepository.findByUsername(username).orElse(null);

           if(user!=null){

               Authentication auth=new UsernamePasswordAuthenticationToken(user,null,null);
               SecurityContextHolder.getContext().setAuthentication(auth);

           }

        }
        filterChain.doFilter(request, response);

    }
    private String extractTokenFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;

    }

}
