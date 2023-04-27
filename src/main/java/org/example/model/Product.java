package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {
    private Integer id;
    private String product_name;
    private Double price;
    private LocalDate date_register;
    private Category category;

    public Product(String product_name, Double price, LocalDate date_register, Category category) {
        this.product_name = product_name;
        this.price = price;
        this.date_register = date_register;
        this.category = category;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", date_register=" + date_register +
                ", category= "+ category.getName() +
                '}'+"\n";
    }
}
