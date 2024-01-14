package Coupon_Project_Spring.Services;

import Coupon_Project_Spring.CustomExceptions.CouponAlreadyPurchasedException;
import Coupon_Project_Spring.CustomExceptions.CouponExpiredException;
import Coupon_Project_Spring.CustomExceptions.CouponsOutOfStockException;
import Coupon_Project_Spring.CustomExceptions.CustomerNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Repositories.CompanyRepository;
import Coupon_Project_Spring.Repositories.CouponRepository;
import Coupon_Project_Spring.Repositories.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * a customer service class that extends the client service class.
 * here we implement the business logic of the customer.
 */
@Service
@Scope("prototype")
public class CustomerService extends ClientService{

    /**
     * the id of the customer currently logged in.
     */
    private int customerId;

    
    public CustomerService(CompanyRepository companyRepo, CustomerRepository customerRepo, CouponRepository couponRepo) {
        super(companyRepo, customerRepo, couponRepo);
    }

    /**
     * Verifies if the provided email and password match the customer credentials.
     * If the credentials match, the customerId is set to the id of the customer that logged in.
     * @param email - the email of the customer.
     * @param password - the password of the customer.
     * @return true if the parameters match the credentials of the customer, false otherwise.
     */
    public boolean login(String email, String password){
        Customer customer = customerRepo.findByEmailAndPassword(email, password); // fewer queries to the database
        if (customer != null) {
            this.customerId = customer.getId();
            return true;
        }else
            return false;
        
    }

    /**
     * A method to return the details of the customer currently logged in.
     * @return the customer entity of the customer currently logged in.
     * @throws CustomerNotFoundException - custom exception when a customer you're trying to get is not in the database.
     */
    public Customer getCustomerDetails() throws CustomerNotFoundException {
        return customerRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Error retrieving customer's details"));
    }
    
    
    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
    

    /**
     * Returns a collection of the coupons of the customer currently logged in.
     * @return set of coupons.
     */
    public Set<Coupon> getAllCustomerCoupons() throws CustomerNotFoundException {
        Customer customer = getCustomerDetails();
        return customer.getCoupons();
    }

    /**
     * Returns a coupon list of the customer currently logged in by category.
     * @param category - the category of the coupons to be returned.
     * @return a list of coupons.
     */
    public List<Coupon> getCouponsByCategory(Category category) throws CustomerNotFoundException{
        Customer customer = getCustomerDetails();
//        return customer.getCoupons().stream().filter(c->c.getCategory().equals(category)).toList();
        return customer.getCoupons().stream().filter(c->c.getCategory().equals(category)).toList();
    }

    /**
     * Returns a coupon list of the customer currently logged in by maximum price.
     * @param price - the maximum price of the coupons to be returned.
     * @return a list of coupons.
     */
    public List<Coupon> getCouponsByMaxPrice(double price) throws CustomerNotFoundException {
        Customer customer = getCustomerDetails();
        return customer.getCoupons().stream().filter(c->c.getPrice()<= price).toList();
    }


    /**
     * In this method, the customer can purchase a coupon.
     * The coupon must not be expired, must be in stock and the customer must not have yet purchased it.
     * @param coupon - the coupon entity to be purchased.
     * @throws CouponExpiredException - custom exception when a coupon you're trying to purchase is expired.
     * @throws CouponAlreadyPurchasedException - custom exception when a coupon you're trying to purchase is already purchased.
     * @throws CouponsOutOfStockException - custom exception when a coupon you're trying to purchase is out of stock.
     */
    public Coupon purchaseCoupon(Coupon coupon) throws CouponExpiredException, CouponAlreadyPurchasedException, CouponsOutOfStockException, CustomerNotFoundException {
        Customer customer = getCustomerDetails();
        if (coupon.getEndDate().isBefore(LocalDate.now()))
            throw new CouponExpiredException("The coupon you are trying to purchase is expired");
        if (customer.getCoupons().stream().anyMatch(c->c.getId() == coupon.getId()))
            throw new CouponAlreadyPurchasedException("Coupon already purchased");
        if (coupon.getAmount() == 0)
            throw new CouponsOutOfStockException("Coupon is out of stock");
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount()-1);
        if (couponRepo.existsById(coupon.getId())){
            couponRepo.save(coupon);
        }

        customerRepo.save(customer);
        return coupon;
    }
    
    
}
