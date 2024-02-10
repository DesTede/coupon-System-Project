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

import java.util.Arrays;
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
    
    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<Coupon> getOneCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body( getService().getOneCoupon(couponId));
    }
    
    @GetMapping("/couponsByCategory/{category}")
    public List<Coupon> getCompanyCouponsByCategory(@PathVariable String category) throws CompanyNotFoundException, UnAuthorizedException, CategoryNotFoundException {
        Category enumCategory = Arrays.stream(Category.values())
                .filter(c -> c.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return getService().getCouponsByCategory(enumCategory);
    }
    
    @GetMapping("/couponsByPrice/{maximum}")
    public List<Coupon> getCompanyCouponsByMaxPrice(@PathVariable double maximum) throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getCouponsByMaxPrice(maximum);
    }
    
    @GetMapping("categories")
    public List<Category> getCategories() throws UnAuthorizedException{ 
        return getService().getCategories();
    }
 
    @PostMapping("/addCoupon")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) throws CompanyNotFoundException, CouponAlreadyExistsException, CouponExpiredException, UnAuthorizedException {
        getService().addCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
    }
    
    @PutMapping("/updateCoupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException, UnAuthorizedException, CompanyNotFoundException {
        getService().updateCoupon(coupon);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(coupon);
    }
    
    @DeleteMapping("/deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        getService().deleteCoupon(couponId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(couponId);
    }
    
    private CompanyService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CompanyService service = (CompanyService) tokenStore.get(token);
        if (service == null)
            throw new UnAuthorizedException("Service is null");
        return service;
    }
    
}
