package Coupon_Project_Spring.Services;

import Coupon_Project_Spring.CustomExceptions.CompanyNotFoundException;
import Coupon_Project_Spring.CustomExceptions.CouponAlreadyExistsException;
import Coupon_Project_Spring.CustomExceptions.CouponExpiredException;
import Coupon_Project_Spring.CustomExceptions.CouponNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Repositories.CompanyRepository;
import Coupon_Project_Spring.Repositories.CouponRepository;
import Coupon_Project_Spring.Repositories.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * a company service class that extends the client service class. 
 * here we implement the business logic of the company.
 */
@Service
@Scope("prototype")
public class CompanyService extends ClientService{

    /**
     * the id of the company currently logged in.
     */
    private int companyId;

    
    public CompanyService(CompanyRepository companyRepo, CustomerRepository customerRepo, CouponRepository couponRepo) {
        super(companyRepo, customerRepo, couponRepo);
    }

    /**
     * Verifies if the provided email and password match the company credentials.
     * If the credentials match, the companyId is set to the id of the company that logged in.
     * @param email - the email of the company.
     * @param password - the password of the company.
     * @return true if the parameters match the credentials of the company, false otherwise.
     */
    public boolean login(String email, String password){
        Company company = companyRepo.findByEmailAndPassword(email, password);
        if (company != null){
            this.companyId = company.getId();
            return true;
        }else {
            return false;
        }
    }

    /**
     * A method to return the details of the company currently logged in.
     * @return the details of the company currently logged in.
     */
    public Company getCompanyDetails() throws CompanyNotFoundException {
        return companyRepo.findById(companyId).orElseThrow(()-> new CompanyNotFoundException("Error retrieving company's details"));
    }


    public List<Coupon> getAllCompanyCoupons() throws CompanyNotFoundException {
        Company company = getCompanyDetails();
        return company.getCoupons();
    }

    /**
     * Returns a coupon if the coupon exists in the company's coupon list.
     * @param couponId - the id of the coupon to be returned.
     * @return the coupon if it exists in the company's coupon list.
     * @throws CouponNotFoundException - custom exception when a coupon you're trying to get is not in the company's coupons.
     * @throws CompanyNotFoundException - custom exception when a company you're trying to get is not in the database.
     */
    public Coupon getOneCoupon(int couponId) throws CouponNotFoundException, CompanyNotFoundException {
        Company company = getCompanyDetails();
        return company.getCoupons().stream().filter(c->c.getId() == couponId).findFirst().orElseThrow(()-> new CouponNotFoundException("Coupon not found in company's coupons"));
    }

    
    /**
     * Returns a list of the company's coupons by category.
     * @param category - the category of the coupons to be returned.
     * @return a list of the company's coupons by category.
     */
    public List<Coupon> getCouponsByCategory(Category category) throws CompanyNotFoundException{
        Company company = getCompanyDetails();
        return company.getCoupons().stream().filter(c->c.getCategory().equals(category)).toList();
    }

    /**
     * Returns a list of the company's coupons by maximum price.
     * @param price - the maximum price of the coupons to be returned.
     * @return a list of the company's coupons by maximum price.
     */
    public List<Coupon> getCouponsByMaxPrice(double price) throws CompanyNotFoundException {
        Company company = getCompanyDetails();
        return company.getCoupons().stream().filter(c->c.getPrice()<= price).toList();
    }
    
    /**
     * Adds a coupon to the company's coupon list if the coupon is not expired, 
     * the coupon title is not already in use and the coupon's company id matches
     * the current logged company's id.
     * @param coupon - coupon to be added to the company
     * @throws CouponAlreadyExistsException - custom exception when a coupon you're trying to add already in the company's coupons.
     */

    
    public Coupon addCoupon(Coupon coupon) throws CouponExpiredException, CompanyNotFoundException, CouponAlreadyExistsException {
        Company company = getCompanyDetails();
        if (coupon.getEndDate().isBefore(LocalDate.now()))
            throw new CouponExpiredException("The coupon you are trying to add is expired");
        if (company.getCoupons().stream().anyMatch(c->c.getTitle().equals(coupon.getTitle())))
            throw new CouponAlreadyExistsException("Coupon title already exists in company's coupons");
        if (coupon.getCompany().getId() != companyId)
            throw new CompanyNotFoundException("Coupon's company id does not match current logged company's id");
        couponRepo.save(coupon);
        return coupon;
    }
    
    /**
     * Updates a coupon if the coupon exists in the company's coupon list.
     * @param coupon - the coupon to be updated.
     * @throws CouponNotFoundException - custom exception when a coupon you're trying to update is not in the company's coupons.
     */
    public Coupon updateCoupon(Coupon coupon) throws CouponNotFoundException{
        if (couponRepo.existsById(coupon.getId())) {
            couponRepo.save(coupon);
            return coupon;
        }
        else throw new CouponNotFoundException("Coupon not found in database");
    }

    /**
     * Deletes a coupon if the coupon exists in the company's coupon list. 
     * It first removes the coupon from all the customers that purchased it.
     * @Transactional - the method is transactional because it deletes from multiple tables.
     * @param couponId - the id of the coupon to be deleted.
     * @throws CouponNotFoundException - custom exception when a coupon you're trying to delete is not in the company's coupons.
     */
    @Transactional
    public void deleteCoupon(int couponId) throws CouponNotFoundException, CompanyNotFoundException {
        Company company = getCompanyDetails();
        List<Customer> customersToUp = new ArrayList<>();
        Coupon coupon = company.getCoupons().stream().filter(c->c.getId() == couponId).findFirst().orElseThrow(()-> new CouponNotFoundException("Coupon not found in company's coupons"));
        
        for (Customer customer:coupon.getCustomers()) {
            customer.getCoupons().remove(coupon);
            customersToUp.add(customer);
        }
        
        customerRepo.saveAll(customersToUp);
        
        coupon.getCustomers().clear();
        
        updateCoupon(coupon);
        couponRepo.deleteById(couponId);
        
        
    }
    
}