package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Services.LoginManager.ClientType;
import Coupon_Project_Spring.Services.LoginManager.LoginManager;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Services.AdminService;
import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Services.CompanyService;
import Coupon_Project_Spring.Services.CustomerService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private LoginManager loginManager;
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;

    public LoginController(LoginManager loginManager, HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
        this.loginManager = loginManager;
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    
    @PostMapping ("/login")    
    public ResponseEntity<String> login(String email, String password, ClientType clientType) throws LoginFailedException, AdminLoginFailedException, CustomerLoginFailedException, CompanyLoginFailedException, CompanyNotFoundException, CustomerNotFoundException {
        ClientService service = loginManager.login(email, password, clientType);
        if (service != null) {
            //login successful
            String token = createToken(service);
            // saving token in the token store
            tokenStore.put(token, service);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        }else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(String token){
        // removes token and the created service from the HashMap 
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(request.getHeader("Authorization").replace("Bearer ", ""));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logged out");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found");
        }
    }
    
    private String createToken(ClientService service) throws CompanyNotFoundException, CustomerNotFoundException {
        String token = "";
        if (service instanceof AdminService){
//            Admin admin = ((AdminService) service);
            Instant expires = Instant.now().plus(30, ChronoUnit.SECONDS);
            token = JWT.create()
                    .withClaim("name","Admin")
                    .withClaim("clientType", ClientType.Administrator.toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());
        }
        else if (service instanceof CompanyService){
            Company company = ((CompanyService) service).getCompanyDetails();
            Instant expires = Instant.now().plus(30, ChronoUnit.SECONDS);
            token = JWT.create()
                    .withClaim("name",company.getName())
                    .withClaim("id", company.getId())
                    .withClaim("email", company.getEmail())
                    .withClaim("clientType", ClientType.Company.toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());
        }
        else if (service instanceof CustomerService){
            Customer customer = ((CustomerService) service).getCustomerDetails();
            Instant expires = Instant.now().plus(30, ChronoUnit.SECONDS);
            token = JWT.create()
                    .withClaim("name",customer.getFirstName() + " " + customer.getLastName())
                    .withClaim("id", customer.getId())
                    .withClaim("email", customer.getEmail())
                    .withClaim("clientType", ClientType.Customer.toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());
        }
        return token;
    }
}
