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

@RestController
@RequestMapping("customer")
//@CrossOrigin
public class CustomerController {
    
    private HashMap<String, ClientService> tokenStore;
    private HttpServletRequest request;
    
    public CustomerController(HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails() throws CustomerNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getService().getCustomerDetails());
    }
    
    @GetMapping("/purchasedCoupons")
    public Set<Coupon> getCustomerCoupons() throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getAllCustomerCoupons();
    }
    
    @GetMapping("/allCoupons")
    public List<Coupon> getAllCoupons() throws UnAuthorizedException {
        return getService().getAllCoupons();
    }
    
    @GetMapping("/coupons/category/{category}")
    public List<Coupon> getCustomerCouponsByCategory(@PathVariable String category) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getCouponsByCategory(Category.valueOf(category));
    }
    
    @GetMapping("/coupons/price/{maximum}")
    public List<Coupon> getCustomerCouponsByMaxPrice(@PathVariable double maximum) throws CustomerNotFoundException, UnAuthorizedException {
        return getService().getCouponsByMaxPrice(maximum);
    }
    
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon) throws CouponsOutOfStockException, CouponAlreadyPurchasedException, CouponExpiredException, CustomerNotFoundException, UnAuthorizedException {
        getService().purchaseCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body("Coupon purchased");
    }

    private CustomerService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CustomerService service =  (CustomerService) tokenStore.get(token);
        if (service == null)
            throw new UnAuthorizedException("Service is null");
        return service;
    }
}
