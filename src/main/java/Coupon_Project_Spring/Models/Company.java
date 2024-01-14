package Coupon_Project_Spring.Models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a Spring entity in the system.
 * It contains information about a company, including its Id (PK, AI, private setter), name, email, password,
 * and a list of coupons associated with the company.
 */

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Setter(AccessLevel.NONE)
    private String name; 
    private  String email;
    private String password;
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private List<Coupon> coupons;


    public Company(String name, String email, String password, List<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public Company( String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id: " + id +
                ", name: " + name + '\'' +
                ", email: " + email + '\'' +
                ", password: " + password + '\'' +
                '}';
    }
}

