package Coupon_Project_Spring.Services;

import Coupon_Project_Spring.CustomExceptions.CustomerNotFoundException;
import Coupon_Project_Spring.Repositories.CompanyRepository;
import Coupon_Project_Spring.Repositories.CouponRepository;
import Coupon_Project_Spring.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * an abstract class that serves as a base class for services related to clients in the Coupon system.
 */
@Service
public abstract class ClientService{

    /**
     * the repositories used for client-related operations.
     */
    protected CompanyRepository companyRepo;
    protected CustomerRepository customerRepo;
    protected CouponRepository couponRepo;

    
    /**
     * a constructor used to initialize the repositories. this way we inject the repositories into the service.
     * @param companyRepo - the company repository.
     * @param customerRepo - the customer repository.
     * @param couponRepo - the coupon repository.
     */
    public ClientService(CompanyRepository companyRepo, CustomerRepository customerRepo, CouponRepository couponRepo) {
        this.companyRepo = companyRepo;
        this.customerRepo = customerRepo;
        this.couponRepo = couponRepo;
    }

    /**
     * a default constructor.
     */
    public ClientService(){
        
    }
    
    /**
     * an abstract method intended to be implemented by the inheriting classes.
     * this method requires concrete implementations in the inheriting classes.
     * @param email - the email of the client.
     * @param password - the password of the client.
     * @return true if the parameters match the credentials of the client.
     */
    public abstract boolean login(String email, String password);
}
