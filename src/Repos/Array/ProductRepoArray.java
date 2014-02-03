package Repos.Array;

import Entities.Product;
import Interfaces.ItemRepo;

public class ProductRepoArray implements ItemRepo<Product> {
    private Product[] products;

    @Override
    public boolean add(Product product) {
        int length = products.length;
        Product[] newProducts;

        for (int counter = 0; counter < length; counter += 1) {
            if (products[counter] == null) {
                products[counter] = product;
                return true;
            }
        }

        newProducts = new Product[length + 1];

        System.arraycopy(products, 0, newProducts, 0, 0);
        this.products = newProducts;

        products[length] = product;

        return true;
    }

    @Override
    public boolean remove(String id) {
        for (int counter = 0, length = products.length; counter < length; counter += 1) {
            if (products[counter] != null && products[counter].getId().equalsIgnoreCase(id)) {
                products[counter] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public Product get(String id) {
        for (Product current : products) {
            if (current != null && current.getId().equalsIgnoreCase(id)) {
                return current;
            }
        }

        return null;
    }

    @Override
    public boolean has(String id) {
        return (this.get(id) != null);
    }

    public boolean replace(String id, Product product) {
        for (int counter = 0, length = products.length; counter < length; counter += 1) {
            if (products[counter] != null && products[counter].getId().equalsIgnoreCase(id)) {
                products[counter] = product;
                return true;
            }
        }

        return false;
    }

    public ProductRepoArray() {
        this.products = new Product[1];
    }
}