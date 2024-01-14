package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/company")
//@CrossOrigin
public class CompanyController {
    
//    private CompanyService companyService;
    private HashMap<String , ClientService> tokenStore;
    private HttpServletRequest request;
    
    public CompanyController( HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
//        this.companyService = companyService;
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    @GetMapping("/details")
    public ResponseEntity<Company> getCompanyDetails() throws CompanyNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getService().getCompanyDetails());
    }
    
    @GetMapping("/coupons")
    public List<Coupon> getCompanyCoupons() throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getAllCompanyCoupons();
    }
    
    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<Coupon> getOneCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body( getService().getOneCoupon(couponId));
    }
    
    @GetMapping("/coupons/category/{category}")
    public List<Coupon> getCompanyCouponsByCategory(@PathVariable String category) throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getCouponsByCategory(Category.valueOf(category));
    }
    
    @GetMapping("/coupons/price/{maximum}")
    public List<Coupon> getCompanyCouponsByMaxPrice(@PathVariable double maximum) throws CompanyNotFoundException, UnAuthorizedException {
        
        return getService().getCouponsByMaxPrice(maximum);
    }
 
    @PostMapping("/coupons")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) throws CompanyNotFoundException, CouponAlreadyExistsException, CouponExpiredException, UnAuthorizedException {
        getService().addCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
    }
    
    @PutMapping("/coupons")
    public ResponseEntity<? > updateCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException, UnAuthorizedException {
        getService().updateCoupon(coupon);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(coupon);
    }
    
    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        getService().deleteCoupon(couponId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Coupon deleted");
    }
    
    private CompanyService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CompanyService service = (CompanyService) tokenStore.get(token);
        if (service == null)
            throw new UnAuthorizedException("Service is null");
        return service;
    }
    
}
