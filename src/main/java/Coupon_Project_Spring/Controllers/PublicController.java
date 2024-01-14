package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController{
    
    private CustomerService service;

    public PublicController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/getCoupons")
    public List<Coupon> getAllCoupons(){
        return service.getAllCoupons();
    }
    
}
