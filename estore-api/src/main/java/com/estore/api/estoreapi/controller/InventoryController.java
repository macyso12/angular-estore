package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

/**
 * ProductController handles the request for the Product resource
 * 
 * @author: itsTeamTwo
 */
@RestController
@RequestMapping("products")
public class InventoryController {

    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDao;

    public InventoryController(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given
     * id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Product
     *         product} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);

        try {
            Product newProduct = inventoryDao.createProduct(product);
            // when productFound is false, meaning product is not found
            if (newProduct != null) {
                return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        LOG.info("GET /products");

        try {
            Product[] product = inventoryDao.getProducts();
            if (product != null)
                return new ResponseEntity<Product[]>(product, HttpStatus.OK);
            else{
                product = new Product[0];
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);

        try {
            Boolean isDelete = inventoryDao.deleteProduct(id);
            if (isDelete)
                return new ResponseEntity<Product>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product products} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all products that contain the text "ee"
     *         GET http://localhost:8080/products/?name=ee
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProduct(@RequestParam String name) {
        LOG.info("GET /products/?name=" + name);
        try {
            Product[] product = inventoryDao.findProducts(name);
            if (product != null)
                return new ResponseEntity<Product[]>(product, HttpStatus.OK);
            else{
                product = new Product[0];
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Product product} with the provided
     * {@linkplain Product product} object, if it exists
     * 
     * @param product The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@linkProduct product} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);

        try {
            // getting the product
            Product updatedProduct = inventoryDao.updateProduct(product);
            // when product is not null, meaning product found
            if (updatedProduct != null) {
                // update name
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
