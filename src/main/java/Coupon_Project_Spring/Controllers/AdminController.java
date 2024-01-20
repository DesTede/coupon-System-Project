package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Services.AdminService;
import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Services.CompanyService;
import com.auth0.jwt.JWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("admin")
//@CrossOrigin
public class AdminController{
 
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;

    public AdminController(HashMap<String, ClientService> tokenStore , HttpServletRequest request) {
        this.request = request;
        this.tokenStore = tokenStore;
    }
    
    @GetMapping("/getCompanies")
    public List<Company> getAllCompanies() throws UnAuthorizedException {
        return getService().getAllCompanies();
    }
    
    @GetMapping("/getCompany/{id}")
    public Company getOneCompany(@PathVariable int id) throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getOneCompany(id);
    }
    
    @GetMapping("/getCustomers")
    public List<Customer> getAllCustomers() throws UnAuthorizedException {
        return getService().getAllCustomers();
    }
    
    @GetMapping("/getCustomer/{id}")
    public Customer getOneCustomer(@PathVariable int id) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getOneCustomer(id);
    }
    
    @PostMapping("/addCompany")
    public ResponseEntity<String> addCompany(@RequestBody Company company) throws CompanyAlreadyExistsException, UnAuthorizedException {
        getService().addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Company added");
    }
    
    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException , UnAuthorizedException {
        getService().addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added");
    }
    
    @PutMapping("/updateCompany")
    public ResponseEntity<String> updateCompany(@RequestBody Company company) throws CompanyNotFoundException , UnAuthorizedException {
        getService().updateCompany(company);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Company updated");
    }
    
    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException , UnAuthorizedException {
        getService().updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Customer updated");
    }
    
    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable int id) throws CompanyNotFoundException, UnAuthorizedException {
        getService().deleteCompany(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Company deleted");
    }
    
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) throws CustomerNotFoundException, UnAuthorizedException {
        getService().deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Customer deleted");
    }

    private AdminService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        AdminService service = (AdminService) tokenStore.get(token);
        if (service == null) {
            throw new UnAuthorizedException("service is null for token: " + token);
        }
        return service;
    }
    
}
    
