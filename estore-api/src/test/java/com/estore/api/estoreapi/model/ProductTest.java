package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product class
 * 
 * @author itsTeamTwo
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "FireWood";
        double expected_price = 23.99;
        String expected_description = "Wood that make fire";
        String expected_image = "a url";

        // Invoke
        Product product = new Product(expected_id, expected_name, expected_price, expected_description, expected_image);

        // Analyze
        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_description, product.getDescription());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Champagne";
        double price = 23.99;
        String description = "Rosie Champagne";
        String image = "a url";
        Product product = new Product(id, name, price, description, image);

        String expected_name = "Galactic Agent";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testDescription() {
        // Setup
        int id = 99;
        String name = "Champagne";
        double price = 23.99;
        String description = "Rosie Champagne";
        String expectedDescription = "Galactic Agent";
        String image = "a url";
        Product product = new Product(id, name, price, description, image);

        // Invoke
        product.setDescription(expectedDescription);

        // Analyze
        assertEquals(expectedDescription, product.getDescription());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 99;
        String name = "Champagne";
        double price = 23.99;
        String description = "Rosie Champagne";
        String image = "a url";
        Product product = new Product(id, name, price, description, image);

        double expectedPrice = 45.99;

        // Invoke
        product.setPrice(expectedPrice);

        // Analyze
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    public void testSetImageURL() {
        // Setup
        int id = 99;
        String name = "Champagne";
        double price = 23.99;
        String description = "Rosie Champagne";
        String image = "a url";
        Product product = new Product(id, name, price, description, image);

        String expectedImage = "a new url";

        // Invoke
        product.setImageURL(expectedImage);

        // Analyze
        assertEquals(expectedImage, product.getImageURL());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Champagne";
        double price = 23.99;
        String description = "Rosie Champagne";
        String image = "a url";
        Product product = new Product(id, name, price, description, image);

        String expectedString = String.format(Product.STRING_FORMAT, id, name, price, description, image);

        // Invoke
        String actual = product.toString();

        // Analyze
        assertEquals(expectedString, actual);
    }

}