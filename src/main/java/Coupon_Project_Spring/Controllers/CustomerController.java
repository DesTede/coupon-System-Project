package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class is a RestController that handles all the requests from the customer.
 * It uses the CustomerService to handle the requests.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    /**
     * This HashMap is used to store the tokens of the logged in users.
     * The key is the token and the value is the service that the token is associated with.
     * HttpServletRequest is used to get the token from the header of the request.
     * Constructor to inject the tokenStore and the request.
     */
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;
    
    public CustomerController(HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    /**
     * This method is used to get the details of the customer.
     * It uses the CustomerService to get the details.
     * @return ResponseEntity with the status and the customer details.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails() throws CustomerNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getService().getCustomerDetails());
    }
    
    /**
     * This method is used to get all the coupons that the customer has purchased.
     * It uses the CustomerService to get the coupons.
     * @return Set of coupons that the customer has purchased.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @GetMapping("/purchasedCoupons")
    public Set<Coupon> getCustomerCoupons() throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getAllCustomerCoupons();
    }
    
    /**
     * This method is used to get all the coupons that the customer can purchase, all the available ones.
     * It uses the CustomerService to get the coupons.
     * @return List of coupons that the customer can purchase.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @GetMapping("/availableCoupons")
    public List<Coupon> getAllCoupons() throws UnAuthorizedException {
        return getService().getAllCoupons();
    }
    
    /**
     * This method is used to get all the coupons that the customer can purchase, by category.
     * It uses the CustomerService to get the coupons.
     * @param category the category of the coupons.
     * @return List of coupons that the customer can purchase, by category.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @GetMapping("/couponsByCategory/{category}")
    public List<Coupon> getCustomerCouponsByCategory(@PathVariable String category) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getCouponsByCategory(Category.valueOf(category));
    }
    
    /**
     * This method is used to get all the coupons that the customer can purchase, by maximum price.
     * It uses the CustomerService to get the coupons.
     * @param maximum the maximum price of the coupons.
     * @return List of coupons that the customer can purchase, by maximum price.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @GetMapping("/couponsByPrice/{maximum}")
    public List<Coupon> getCustomerCouponsByMaxPrice(@PathVariable double maximum) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getCouponsByMaxPrice(maximum);
    }
    
    /**
     * This method is used to purchase a coupon.
     * It uses the CustomerService to purchase the coupon.
     * @param couponId the id of the coupon to purchase.
     * @return ResponseEntity with the status and a message.
     * @throws CouponsOutOfStockException if the coupon is out of stock.
     * @throws CouponAlreadyPurchasedException if the coupon has already been purchased.
     * @throws CouponExpiredException if the coupon has expired.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    @PostMapping("/purchaseCoupon/{couponId}")
    public ResponseEntity<?> purchaseCoupon( @PathVariable int couponId) throws CouponsOutOfStockException, CouponAlreadyPurchasedException, CouponExpiredException, CustomerNotFoundException, UnAuthorizedException {
        getService().purchaseCoupon(couponId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Coupon purchased");
    }

    /**
     * This method is used to get the customer service from the tokenStore.
     * @return the customer service.
     * @throws UnAuthorizedException if the token is not found in the tokenStore.
     */
    private CustomerService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CustomerService service =  (CustomerService) tokenStore.get(token);
        if (service == null)
            throw new UnAuthorizedException("Service is null");
        return service;
    }
}
