package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test the InventoryFileDAO class
 * 
 * @author itsTeamTwo
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
        Product[] testProducts;
        InventoryFileDAO inventoryFileDAO;
        ObjectMapper mockObjectMapper;

        @BeforeEach
        public void setupInventoryFileDAO() throws IOException {
                mockObjectMapper = mock(ObjectMapper.class);
                testProducts = new Product[3];

                testProducts[0] = new Product(99, "Burt's Bees Lip Balms", 11.49,
                                "4 Value Pack of Burt's Bees Lip Balms",
                                "a url");
                testProducts[1] = new Product(100, "Tomatoes", 5.79, "24 oz Organic Tomatoes on the vine",
                                "a url");
                testProducts[2] = new Product(101, "White Chocolate", 4.09, "3.2 oz white chocolate with 30% cocoa",
                                "a url");

                when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), Product[].class))
                                .thenReturn(testProducts);
                inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
        }

        @Test
        public void testGetProducts() {
                // Invoke
                Product[] products = inventoryFileDAO.getProducts();

                // Analyze
                assertEquals(products.length, testProducts.length);
                for (int i = 0; i < products.length; i++) {
                        assertEquals(products[i], testProducts[i]);
                }
        }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = inventoryFileDAO.getProduct(99);

        // Analyze
        assertEquals(product, testProducts[0]);
    }

        @Test
        public void testFindProduct() {
                // Invoke
                Product[] products = inventoryFileDAO.findProducts("o");

                // Analyze
                assertEquals(products.length, 2);
                assertEquals(products[0], testProducts[1]);
                assertEquals(products[1], testProducts[2]);
        }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(102, "Girlfriend", 99.99, "Buy yourself a new girlfriend", "a url");

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(), product.getId());
        assertEquals(actual.getName(), product.getName());
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        Product product = new Product(99,"Redbull", 8.99, "A can of red bull", "a url");
        
        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        // Analyze 
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual, product);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Product[].class));

        Product product = new Product(102, "Burt's Bees Lip Balms", 11.49, "4 Value Pack of Burt's Bees Lip Balms", "a url");

        assertThrows(IOException.class, () -> inventoryFileDAO.createProduct(product),
                "Expected exception not thrown");
    }

    @Test
    public void testGetProductNotFound() {
         // Invoke
        Product product = inventoryFileDAO.getProduct(98);

         // Analyze
         assertEquals(product,null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(inventoryFileDAO.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(98, "Fish", 44.99, "Large Slimy Fish", "a url");

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException{
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the InventoryFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
