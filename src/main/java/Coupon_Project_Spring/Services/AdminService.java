package Coupon_Project_Spring.Services;

import Coupon_Project_Spring.CustomExceptions.*;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Models.Customer;
import Coupon_Project_Spring.Repositories.CompanyRepository;
import Coupon_Project_Spring.Repositories.CouponRepository;
import Coupon_Project_Spring.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * an admin service class that extends the client service class. 
 * here we implement the business logic of the admin.
 */
@Service
public class AdminService extends ClientService{


    public AdminService(CompanyRepository companyRepo, CustomerRepository customerRepo, CouponRepository couponRepo) {
        super(companyRepo, customerRepo, couponRepo);
    }

    /**
     * a method that Verifies if the provided email and password match the admin credentials.
     * @param email - the email of the client.
     * @param password - the password of the client.
     * @return true if the parameters match the credentials of the client.
     */
    public boolean login(String email, String password){
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public List<Company> getAllCompanies(){
        return companyRepo.findAll();
    }

    /**
     * Returns a company if the company exists in the database.
     * @param id - the id of the company to be returned.
     * @return the company entity.
     * @throws CompanyNotFoundException - custom exception when a company you're trying to get is not in the database.
     */
    public Company getOneCompany(int id) throws CompanyNotFoundException {
        return companyRepo.findById(id).orElseThrow(()->new CompanyNotFoundException("Company not found"));
    }
    
    
    /**
     * Adds a new company if the email or name is not already in use.
     * @param company - the company entity to be added.
     * @throws CompanyAlreadyExistsException - custom exception when a company you're trying to add already in the database.
     */
    public Company addCompany(Company company) throws CompanyAlreadyExistsException{
        if (companyRepo.existsByEmailOrName(company.getEmail(),company.getName()))
            throw new CompanyAlreadyExistsException("Company name or email already in use");
        else {
            System.out.println("Company " + company.getName() + " added");
            companyRepo.save(company);
            return company;
        }
    }

    /**
     * Updates a company if the company exists in the database.
     * @param company - the company entity to be updated.
     * @throws CompanyNotFoundException - custom exception when a company you're trying to update is not in the database.
     */
    public Company updateCompany(Company company) throws CompanyNotFoundException{
        if (companyRepo.existsById(company.getId())) {
            companyRepo.save(company);
            return company;
        }else 
            throw new CompanyNotFoundException("Company not found");
    }

    /**
     * Deletes a company if the company exists in the database.
     * The method deletes all the coupons of the company and all the purchases of the company's coupons. 
     * Then it deletes the company.
     * @Transactional - the method is transactional because it deletes from multiple tables.
     * @param companyId - the id of the company to be deleted.
     * @throws CompanyNotFoundException - custom exception when a company you're trying to delete is not in the database.
     */
    @Transactional
    public void deleteCompany(int companyId) throws CompanyNotFoundException {
        Company companyToDelete = companyRepo.findById(companyId).orElseThrow(()->new CompanyNotFoundException("Company not found"));
        List<Customer> customersToUp = new ArrayList<>();
        List<Coupon> couponsToUp = new ArrayList<>();
        
        for (Coupon coupon:companyToDelete.getCoupons()) {
            for (Customer customer : coupon.getCustomers()) {
                customer.getCoupons().remove(coupon);
                customersToUp.add(customer);
            }
        }
        customerRepo.saveAll(customersToUp);
        
        for (Coupon c:companyToDelete.getCoupons()){
            c.getCustomers().clear();
            couponsToUp.add(c);
        }
        couponRepo.saveAll(couponsToUp);
        
        updateCompany(companyToDelete);
        couponRepo.deleteCouponsByCompanyId(companyToDelete.getId());
        companyRepo.deleteById(companyId);
    }

    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    /**
     * Returns a customer if the customer exists in the database.
     * @param id - the id of the customer to be returned.
     * @return the customer entity.
     * @throws CustomerNotFoundException - custom exception when a customer you're trying to get is not found in the database.
     */
    public Customer getOneCustomer(int id) throws CustomerNotFoundException {
        return customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
    }
    

    /**
     * Adds a new customer to the database if the email is not already in use.
     * @param customer - the customer entity to be added.
     * @throws CustomerAlreadyExistsException - custom exception when a customer you're trying to add already in the database.
     */
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepo.existsByEmail(customer.getEmail()))
            throw new CustomerAlreadyExistsException("Customer's email already exists");
        customerRepo.save(customer);
        return customer;
    }

    /**
     * Updates a customer if the customer exists in the database.
     * @param customer - the customer entity to be updated.
     * @throws CustomerNotFoundException - custom exception when a customer you're trying to update is not in the database.
     */
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        if (customerRepo.existsById(customer.getId())) {
            customerRepo.save(customer);
            return customer;
        }else
            throw new CustomerNotFoundException("Customer not found");
    }

    /**
     * Deletes a customer if the customer exists in the database. 
     * Also deletes all the purchases of the customer.
     * @Transactional - the method is transactional because it deletes from multiple tables.    
     * @param id - the id of the customer to be deleted.
     * @throws CustomerNotFoundException
     */
    @Transactional
    public void deleteCustomer(int id) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        customer.getCoupons().clear();
        updateCustomer(customer);
        customerRepo.deleteById(id);
    }
    
    
}
