package Coupon_Project_Spring.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null){ // means no token from the client
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("You are not authorized to access this resource");
        }else{
            token = token.replace("Bearer ", "");
            try {
                JWT.decode(token).getClaim("clientType").asString();
                // if we got here, it means the token is valid, and we can continue to the next filter
                filterChain.doFilter(request, response);
            }catch (JWTDecodeException ex){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("You are not authorized");
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> patterns = List.of("/v3/api-docs", "/configuration/", "/swagger", "/webjars",
                "/auth/login", "/public","/actuator/health");
        return patterns.stream().anyMatch( p-> request.getRequestURL().toString().contains(p));
    }
}
