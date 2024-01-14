package Coupon_Project_Spring.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class CORSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
            response.setHeader("Access-Control-Allow-Origin", "*"); //"http://localhost:3000"
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "authorization, Origin, Accept, Content-type, Access-Control-Request-Method, Access-Control-Request-Headers");
        
            if (request.getMethod().equals("OPTIONS")) { //preflight request
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }else 
                filterChain.doFilter(request, response);
            
            // in ajax we make two requests, one is OPTIONS and the other is the actual request.
            // the preflight request is sent, and if the server responds with 200, the actual request is sent. 
    }
}
