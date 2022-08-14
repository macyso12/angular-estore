package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product class represents a Product Entity
 * Products have the following attributes
 * id
 * name
 * price (00.00)
 * description
 * 
 * @author: itsTeamTwo
 */
public class Product {
    // properties of the Product
    static final String STRING_FORMAT = "Product[id=%d, name=%s, price=%f, description=%s, imageURL=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private double price;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageURL")
    private String imageURL;

    /**
     * Creates a product
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param price       The price of the product
     * @param description The description of the product
     */
    public Product(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("price") double price,
            @JsonProperty("description") String description,
            @JsonProperty("imageURL") String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    /**
     * Gets the product id
     * 
     * @return product id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the product name
     * 
     * @return product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the product price
     * 
     * @return product price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Gets the product description
     * 
     * @return product description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the product name
     * 
     * @param name The product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product price
     * 
     * @param price The product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the product description
     * 
     * @param description The product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product image URL
     * 
     * @return product image URL
     */
    public String getImageURL() {
        return this.imageURL;
    }

    /**
     * Sets the product image URL
     * 
     * @param imageURL The product image URL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.name, this.price, this.description, this.imageURL);
    }
}
