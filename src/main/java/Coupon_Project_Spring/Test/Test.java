package Coupon_Project_Spring.Test;

import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Services.CompanyService;
import Coupon_Project_Spring.Services.LoginManager.ClientType;
import Coupon_Project_Spring.Services.LoginManager.LoginManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Test class,a Spring component, covering various aspects of your coupon management system.
 */
@Component
public class Test {
    
    private LoginManager loginManager;

    
    public Test(LoginManager loginManager) {
        this.loginManager = loginManager;

    }
    public void testAll() throws Exception {
        
        
//        AdminService adminService = (AdminService) loginManager.login("admin@admin.com","admin", ClientType.Administrator);
//        CompanyService companyService = (CompanyService) loginManager.login("nike@waffel.com", "password", ClientType.Company);
//        CustomerService customerService = (CustomerService) loginManager.login("eatmyshorts@gmail.com","milhouse",ClientType.Customer);
        
//        Company company = new Company("JBL","jbl.com","jabal",new ArrayList<>());
//        Company company1 = new Company("Brooks","b@email.com","brooks");

//        Customer customer = new Customer("Homer","Simpson","doughnut@gmail.com","lard");
//        Customer customer1 = new Customer("Marge","Simpson","blue@gmail.com","maggie");
//        Customer customer2 = new Customer("Maggie","Simpson","pacifier@gmail.com","pacifier");
//        Customer customer = new Customer("John","last","gmail","password");`
//        Company company = companyService.getCompanyDetails();
//        Coupon coupon = new Coupon(11, Category.Books,"science Fiction","Dune","", LocalDate.of(2023,11,14),LocalDate.of(2024,1,29),23.54);
//        Coupon coupon1 = new Coupon(company,1, Category.Baby,"Washcloth","The Chronic Vinyl Record"," ",LocalDate.of(2023,11,14),LocalDate.of(2024,1,29),25.54);
//        Coupon coupon2 = new Coupon(company,2,Category.Education,"Full Stack Web","comprehensive Client side course","",LocalDate.of(2023,11,30),LocalDate.of(2024,1,30),13_000);


//        adminService.addCustomer(customer);
//        adminService.addCustomer(customer1);
//        adminService.addCustomer(customer2);
//        adminService.addCompany(company);
//        adminService.addCompany(company1);
//        Company company = adminService.getOneCompany(3);
//        company.setEmail("nike@waffel.com");
//        company.setPassword("shoe_dog");
//        adminService.updateCompany(company);
//        company1.setEmail("jabal@hgmail.com");
//        adminService.updateCompany(company1);
//        System.out.println(adminService.getOneCompany(4));
//        System.out.println(adminService.getOneCustomer(2));
//        for (Company c:adminService.getAllCompanies())
//            System.out.println(c);
//        for (Customer c:adminService.getAllCustomers())
//            System.out.println(c);
        
//        Customer customer = adminService.getOneCustomer(5);
//        customer.setPassword("milhouse");
//        customer.setEmail("eatmyshorts@gmail.com");
//        customer.setFirstName("bart");
//        customer.setLastName("simpson");
//        adminService.updateCustomer(customer);
//        System.out.println(customer);
//        adminService.deleteCompany(2);
//        adminService.deleteCustomer(6);


//        companyService.addCoupon(coupon);

//        companyService.addCoupon(coupon);
//        companyService.addCoupon(coupon1);
//        companyService.addCoupon(coupon2);

//        Coupon coupon = companyService.getOneCoupon(25);
//        coupon.setPrice(15_300);
//        coupon.setEndDate(LocalDate.of(2023,12,31));
//        coupon.setStartDate(LocalDate.of(2023,9,1));
//        companyService.updateCoupon(coupon);
//        System.out.println(coupon);
//        companyService.deleteCoupon(4);
//        System.out.println(companyService.getCompanyDetails());
//        for (Coupon coupon: companyService.getCouponsByCategory(Music))
//            System.out.println(coupon);
//        System.out.println("----------");
//        for (Coupon coupon:companyService.getCouponsByMaxPrice(50))
//            System.out.println(coupon);
//        System.out.println(company1);
//        System.out.println(companyService.getOneCoupon(3));
//        companyService.deleteCoupon(18);
//        Company company = companyService.getCompanyDetails();
//        System.out.println(company);
//        companyService.addCoupon(coupon1);
//        companyService.updateCoupon(coupon1);
//        System.out.println(customer);
//        Coupon coupon = companyService.getOneCoupon(4);
//        coupon.setPrice(100);
//        companyService.updateCoupon(coupon);
//        System.out.println(coupon);
//        companyService.deleteCoupon(2);
//        coupon.setTitle("Dune");
//        companyService.updateCoupon(coupon);
//        coupon.setDescription("Dune is a 1965 epic science fiction novel by American author Frank Herbert");
//        companyService.updateCoupon(coupon);
//        Company company = companyService.getCompanyDetails();
//        companyService.deleteCoupon(3);




//        Coupon coupon = companyService.getOneCoupon(23);
//        Coupon coupon1 = companyService.getOneCoupon(25);
//        System.out.println(coupon);
//        Coupon coupon1 = companyService.getOneCoupon(20);
//        customerService.purchaseCoupon(coupon1);
//        customerService.purchaseCoupon(coupon);
//        System.out.println(customerService.getCustomerDetails());
//        customerService.getAllCustomerCoupons().forEach(System.out::println);
//        System.out.println("------------");
//        customerService.getCouponsByMaxPrice(24).forEach(System.out::println);
//        System.out.println("------------");
//        customerService.getCouponsByCategory(Category.Baby).forEach(System.out::println);
        
        

        
        
    }
}
