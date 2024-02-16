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

/**
 * This class is a RestController that handles all the requests from the company.
 * The class is responsible for handling all the requests from the company.
 * The class is a RestController and is mapped to "/company".
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    
    /**
     * This field is a HashMap that stores the tokens of the logged in users.
     * The field is used to validate the tokens of the users.
     * HttpServletRequest is used to get the token from the request header.
     * We have a constructor for injection.
     */
    private HashMap<String , ClientService> tokenStore;
    private HttpServletRequest request;
    
    public CompanyController( HashMap<String, ClientService> tokenStore, HttpServletRequest request) {
        this.tokenStore = tokenStore;
        this.request = request;
    }
    
    /**
     * This method is a GetMapping method that returns the details of the company.
     * The method is mapped to "/details".
     * The method calls the getCompanyDetails method from the CompanyService.
     * @return the details of the company
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    
    @GetMapping("/details")
    public ResponseEntity<Company> getCompanyDetails() throws CompanyNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getService().getCompanyDetails());
    }
    
    /**
     * This method is a GetMapping method that returns all the coupons of the company.
     * The method is mapped to "/coupons".
     * The method calls the getAllCompanyCoupons method from the CompanyService.
     * @return list of coupons
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/coupons")
    public List<Coupon> getCompanyCoupons() throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getAllCompanyCoupons();
    }
    
    /**
     * This method is a GetMapping method that returns a single coupon of the company.
     * The method is mapped to "/coupon/{couponId}".
     * The method calls the getOneCoupon method from the CompanyService.
     * @param couponId - the id of the coupon to return.
     * @return a single coupon
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws CouponNotFoundException - if the coupon is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<Coupon> getOneCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body( getService().getOneCoupon(couponId));
    }
    
    /**
     * This method is a GetMapping method that returns all the coupons of the company by category.
     * The method is mapped to "/couponsByCategory/{category}".
     * The method calls the getCouponsByCategory method from the CompanyService.
     * @param category - the category of the coupons to return.
     * @return list of coupons
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     * @throws CategoryNotFoundException - if the category is not found.
     */
    @GetMapping("/couponsByCategory/{category}")
    public List<Coupon> getCompanyCouponsByCategory(@PathVariable String category) throws CompanyNotFoundException, UnAuthorizedException, CategoryNotFoundException {
        Category enumCategory = Arrays.stream(Category.values())
                .filter(c -> c.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return getService().getCouponsByCategory(enumCategory);
    }
    
    /**
     * This method is a GetMapping method that returns all the coupons of the company by maximum price.
     * The method is mapped to "/couponsByPrice/{maximum}".
     * The method calls the getCouponsByMaxPrice method from the CompanyService.
     * @param maximum - the maximum price of the coupons to return.
     * @return list of coupons
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("/couponsByPrice/{maximum}")
    public List<Coupon> getCompanyCouponsByMaxPrice(@PathVariable double maximum) throws CompanyNotFoundException, UnAuthorizedException {
        return getService().getCouponsByMaxPrice(maximum);
    }
    
    /**
     * This method is a GetMapping method that returns all the categories of the coupons.
     * The method is mapped to "/categories".
     * The method calls the getCategories method from the CompanyService.
     * @return list of categories
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @GetMapping("categories")
    public List<Category> getCategories() throws UnAuthorizedException{ 
        return getService().getCategories();
    }
 
    /**
     * This method is a PostMapping method that adds a coupon to the database.
     * The method is mapped to "/addCoupon".
     * The method calls the addCoupon method from the CompanyService.
     * @param coupon - the coupon to add.
     * @return the added coupon
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws CouponAlreadyExistsException - if the coupon already exists.
     * @throws CouponExpiredException - if the coupon is expired.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @PostMapping("/addCoupon")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) throws CompanyNotFoundException, CouponAlreadyExistsException, CouponExpiredException, UnAuthorizedException {
        getService().addCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
    }
    
    /**
     * This method is a PutMapping method that updates a coupon in the database.
     * The method is mapped to "/updateCoupon".
     * The method calls the updateCoupon method from the CompanyService.
     * @param coupon - the coupon to update.
     * @return the updated coupon
     * @throws CouponNotFoundException - if the coupon is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     * @throws CompanyNotFoundException - if the company is not found.
     */
    @PutMapping("/updateCoupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException, UnAuthorizedException, CompanyNotFoundException {
        getService().updateCoupon(coupon);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(coupon);
    }
    
    /**
     * This method is a DeleteMapping method that deletes a coupon from the database.
     * The method is mapped to "/deleteCoupon/{couponId}".
     * The method calls the deleteCoupon method from the CompanyService.
     * @param couponId - the id of the coupon to delete.
     * @return the id of the deleted coupon
     * @throws CompanyNotFoundException - if the company is not found.
     * @throws CouponNotFoundException - if the coupon is not found.
     * @throws UnAuthorizedException - if the token is not valid.
     */
    @DeleteMapping("/deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId) throws CompanyNotFoundException, CouponNotFoundException, UnAuthorizedException {
        getService().deleteCoupon(couponId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(couponId);
    }
    
    /**
     * This method is a method that returns the service of the company.
     * The method is used to validate the token of the user.
     * @return the service of the company
     * @throws UnAuthorizedException - if the token is not valid.
     */
    private CompanyService getService() throws UnAuthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CompanyService service = (CompanyService) tokenStore.get(token);
        if (service == null)
            throw new UnAuthorizedException("Service is null");
        return service;
    }
    
}
