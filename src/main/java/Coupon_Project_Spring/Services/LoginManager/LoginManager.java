package Coupon_Project_Spring.Services.LoginManager;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Services.AdminService;
import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Services.CompanyService;
import Coupon_Project_Spring.Services.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * this Spring service component is designed to handle user authentication and return the appropriate service 
 * based on the provided credentials and client type.
 */
@Service
public class LoginManager{

    /**
     * context is used to get the correct service based on the client type.
     */
    ApplicationContext context;
    
    public LoginManager(ApplicationContext context) {
        this.context = context;
    }

    /**
     * this method is used to allow a user to log in to the system using email, password and client type.
     * if the input data match, then the correct service will be returned and the user will be logged in as the matching client type.
     * @param email the user's email for authentication.
     * @param password the user's password for authentication.
     * @param clientType the user's client type enum for authentication.
     * @return adminService, companyService or customerService if login successful. if login failed, then null is returned.
     * @throws AdminLoginFailedException if the admin login failed.
     * @throws CustomerLoginFailedException if the customer login failed.
     * @throws CompanyLoginFailedException if the company login failed.
     
     */
    public ClientService login(String email, String password, ClientType clientType) throws CustomerLoginFailedException, CompanyLoginFailedException, AdminLoginFailedException, LoginFailedException, CompanyNotFoundException {
        
        if (clientType == (ClientType.Administrator)){
            AdminService admin = context.getBean(AdminService.class);
            if (admin.login(email, password)) {
                System.out.println("Admin login successful");
                return admin;
            }else 
                throw new AdminLoginFailedException();
            
        } else if (clientType == (ClientType.Company)) {
            CompanyService company = context.getBean(CompanyService.class);
            if (company.login(email,password)) {
                System.out.println("Company: " +company.getCompanyDetails().getId() + " login successful");
                return company;
            }else 
                throw new CompanyLoginFailedException();
            
        } else if (clientType == (ClientType.Customer)) {
            CustomerService customer = context.getBean(CustomerService.class);
            if (customer.login(email, password)) {
                System.out.println("Customer login successful");
                return customer;
            }else 
                throw new CustomerLoginFailedException();
        }
        return null;
    }
}
