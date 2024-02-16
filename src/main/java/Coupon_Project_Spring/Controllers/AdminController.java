package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Services.AdminService;
import Coupon_Project_Spring.Services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * This class is a RestController that handles all the requests from the admin.
 * The class is responsible for handling all the requests from the admin.
 * The class is a RestController and is mapped to "/admin".
 * The class has a method for each request from the admin.
 */
@RestController
@RequestMapping("/admin")
public class AdminController{
 
    /**
     * This field is a HashMap that stores the tokens of the logged in users.
     * The field is used to validate the tokens of the users.
     * HttpServletRequest is used to get the token from the request header.
     * we have a constructor to inject the fields.
     */
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;

    public AdminController(HashMap<String, ClientService> tokenStore , HttpServletRequest request) {
        this.request = request;
        this.tokenStore = tokenStore;
    }
    

    /**
     * This method is a GetMapping method that returns all the companies in the database.
     * The method is mapped to "/getcompanies".
     * The method calls the getAllCompanies method from the AdminService.
     * @return list of companies
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/getcompanies")
    public List<Company> getAllCompanies() throws UnAuthorizedException {
        return getService().getAllCompanies();
    }
    
    /**
     * This method is a GetMapping method that returns a single company from the database.
     * The method is mapped to "/getcompany/{id}".
     * The method calls the getOneCompany method from the AdminService.
     * @param id - the id of the company to return.
     * @return a single company
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/getcompany/{id}")
    public Company getOneCompany(@PathVariable int id) throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getOneCompany(id);
    }
    
    
    /**
     * This method is a GetMapping method that returns all the customers in the database.
     * The method is mapped to "/getcustomers".
     * The method calls the getAllCustomers method from the AdminService.
     * @return list of customers
     * @throws UnAuthorizedException - if the token is not valid.
     */

    @GetMapping("/getcustomers")
    public List<Customer> getAllCustomers() throws UnAuthorizedException {
        return getService().getAllCustomers();
    }
    
    
    /**
     * This method is a GetMapping method that returns a single customer from the database.
     * The method is mapped to "/getcustomer/{id}".
     * The method calls the getOneCustomer method from the AdminService.
     * @param id - the id of the customer to return.
     * @return a single customer
     * @throws CustomerNotFoundException - if the customer is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/getcustomer/{id}")
    public Customer getOneCustomer(@PathVariable int id) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getOneCustomer(id);
    }
    
    /**
     * This method is a PostMapping method that adds a company to the database.
     * The method is mapped to "/addcompany".
     * The method calls the addCompany method from the AdminService.
     * @param company - the company to add.
     * @return the added company
     * @throws CompanyAlreadyExistsException - if the company already exists.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @PostMapping("/addcompany")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws CompanyAlreadyExistsException, UnAuthorizedException {
        getService().addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }
    
    /**
     * This method is a PostMapping method that adds a customer to the database.
     * The method is mapped to "/addcustomer".
     * The method calls the addCustomer method from the AdminService.
     * @param customer - the customer to add.
     * @return the added customer
     * @throws CustomerAlreadyExistsException - if the customer already exists.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @PostMapping("/addcustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException , UnAuthorizedException {
        getService().addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
    /**
     * This method is a PutMapping method that updates a company in the database.
     * The method is mapped to "/updatecompany".
     * The method calls the updateCompany method from the AdminService.
     * @param company - the company to update.
     * @return the updated company
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @PutMapping("/updatecompany")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) throws CompanyNotFoundException , UnAuthorizedException {
        getService().updateCompany(company);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(company);
    }
    
    /**
     * This method is a PutMapping method that updates a customer in the database.
     * The method is mapped to "/updatecustomer".
     * The method calls the updateCustomer method from the AdminService.
     * @param customer - the customer to update.
     * @return the updated customer
     * @throws CustomerNotFoundException - if the customer is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @PutMapping("/updatecustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException , UnAuthorizedException {
        getService().updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer);
    }
    
    /**
     * This method is a DeleteMapping method that deletes a company from the database.
     * The method is mapped to "/deletecompany/{id}".
     * The method calls the deleteCompany method from the AdminService.
     * @param id - the id of the company to delete.
     * @return a string that says the company was deleted.
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @DeleteMapping("/deletecompany/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable int id) throws CompanyNotFoundException, UnAuthorizedException {
        getService().deleteCompany(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Company deleted");
    }
    
    /**
     * This method is a DeleteMapping method that deletes a customer from the database.
     * The method is mapped to "/deletecustomer/{id}".
     * The method calls the deleteCustomer method from the AdminService.
     * @param id - the id of the customer to delete.
     * @return a string that says the customer was deleted.
     * @throws CustomerNotFoundException - if the customer is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    
    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) throws CustomerNotFoundException, UnAuthorizedException {
        getService().deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Customer deleted");
    }

    /**
     * This method is a private method that returns the AdminService.
     * The method is used to get the AdminService from the token.
     * @return AdminService
     * @throws UnAuthorizedException - if the token is not valid.
     */
    private AdminService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        AdminService service = (AdminService) tokenStore.get(token);
        if (service == null) {
            throw new UnAuthorizedException("service is null for token: " + token);
        }
        return service;
    }
    
}
    
