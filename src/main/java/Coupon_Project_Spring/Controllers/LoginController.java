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

/**
 * This class is a RestController that handles the login and logout requests.
 * It uses the LoginManager to authenticate the user and create a token for the user.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {
    
    /**
     * defining the fields and injecting them using the constructor
     */
    private LoginManager loginManager;
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;

    public LoginController(LoginManager loginManager, HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
        this.loginManager = loginManager;
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    /**
     * This method handles the login request.
     * It receives the email, password and clientType from the user and uses the
     * loginManager to authenticate the user.
     * If the login is successful, it creates a token for the user and saves 
     * it in the token store.
     * @param email the user's email
     * @param password the user's password
     * @param clientType the user's client type
     * @return ResponseEntity with the token if the login is successful, or a message if the login failed.
     * @throws LoginFailedException the login failed
     * @throws AdminLoginFailedException the admin login failed
     * @throws CustomerLoginFailedException the customer login failed
     * @throws CompanyLoginFailedException the company login failed
     * @throws CompanyNotFoundException the company not found
     * @throws CustomerNotFoundException the customer not found
     */
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
    
    
    /**
     * This method handles the logout request.
     * It receives the token from the user and removes it from the token store.
     * @return ResponseEntity with a message if the logout is successful,
     * or a message if the token is not found.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        if (!token.isEmpty()) {
            tokenStore.remove(token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logged out");
        }
        else 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found");
    }
    
    /**
     * This method creates a token for the user.
     * It receives the user's service and creates a token with the user's details.
     * @param service the user's service
     * @return the token
     * @throws CompanyNotFoundException if the company not found
     * @throws CustomerNotFoundException if the customer not found
     */
    private String createToken(ClientService service) throws CompanyNotFoundException, CustomerNotFoundException {
        String token = "";
        
        if (service instanceof AdminService){
//            Admin admin = ((AdminService) service);
            Instant expires = Instant.now().plus(10, ChronoUnit.MINUTES);
            token = JWT.create()
                    .withClaim("name","Admin")
                    .withClaim("clientType", ClientType.Administrator.toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());
        }
        else if (service instanceof CompanyService){
            Company company = ((CompanyService) service).getCompanyDetails();
            Instant expires = Instant.now().plus(10, ChronoUnit.MINUTES);
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
            Instant expires = Instant.now().plus(10, ChronoUnit.MINUTES);
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
