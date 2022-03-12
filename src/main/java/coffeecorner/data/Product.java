package coffeecorner.data;

import coffeecorner.service.PriceTypeService;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private Type type;
    private double price;
    private List<Product> extras = new ArrayList<>();

    public Product() {
    }

    public Product(String name) {
        this.name = name;
        this.type = PriceTypeService.getTypeForProductName(name);
        this.price = PriceTypeService.getPriceForProductName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Product> getExtras() {
        return extras;
    }

    public void setExtras(List<Product> extras) {
        this.extras = extras;
    }
}
