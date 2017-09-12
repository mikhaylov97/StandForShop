package com.tsystems.stand.model;

/**
 * Product model.
 */
public class Product {

    /**
     * Product ID.
     */
    private long id;

    /**
     * Product name.
     */
    private String name;

    /**
     * Product cost.
     */
    private String price;

    /**
     * Url which should return product image.
     */
    private String image;

    /**
     * Product total number of sales.
     */
    private String numberOfSales;

    /**
     * Empty constructor.
     */
    public Product() {
    }

    /**
     * Custom constructor to initialize product with necessary values.
     * @param name that must be set.
     * @param price that must be set.
     * @param image url that must be set.
     */
    public Product(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    /**
     * Custom constructor to initialize product with necessary values.
     * @param name that must be set.
     * @param price that must be set.
     * @param image url that must be set.
     * @param id that must be set.
     * @param numberOfSales that must be set.
     */
    public Product(long id, String name, String price, String image, String numberOfSales) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.numberOfSales = numberOfSales;
    }

    /**
     * Simple getter.
     * @return product ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter.
     * @param id that must be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter.
     * @return product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter.
     * @param name that must be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter.
     * @return product price.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Simple setter.
     * @param price that must be set.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Simple getter.
     * @return product image url.
     */
    public String getImage() {
        return image;
    }

    /**
     * Simple setter.
     * @param image url that must be set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Simple getter.
     * @return product
     * total number of sales.
     */
    public String getNumberOfSales() {
        return numberOfSales;
    }

    /**
     * Simple setter.
     * @param numberOfSales that must be set.
     */
    public void setNumberOfSales(String numberOfSales) {
        this.numberOfSales = numberOfSales;
    }
}
