package Coupon_Project_Spring.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * this class represents a Spring entity in the system.
 * it contains information about the coupon including id(PK, AI, private setter),
 * the company it's associated with, amount in stock, category, title, description, start and end dates
 * its price and a list of customers who purchased it.
 */

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
public class Coupon {
    
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    private Company company;
    private int amount;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    
    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
    private Set<Customer> customers;

    public Coupon(Company company, int amount, Category category, String title, String description, String image, LocalDate startDate, LocalDate endDate, double price) {
        this.company = company;
        this.amount = amount;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company name: " + company.getName() +
                ", company id: " + company.getId() +
                ", company email: " + company.getEmail() +
                ", amount: " + amount +
                ", category: " + category +
                ", title: " + title + '\'' +
                ", description: " + description + '\'' +
                ", startDate: " + startDate +
                ", endDate: " + endDate +
                ", price: " + price +
                ", customers: " + customers.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && amount == coupon.amount && Double.compare(price, coupon.price) == 0 && Objects.equals(company, coupon.company) && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, amount, category, title, description, image, startDate, endDate, price);
    }


    @JsonGetter("company")
    public String getCompanyData(){
        return company.getName() + ", company email: " + company.getEmail();
    }
}

