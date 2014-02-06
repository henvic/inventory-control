package Repos.Array;

import Entities.ProductPrototype;
import Interfaces.ItemRepo;

public class ProductPrototypeRepoArray implements ItemRepo<ProductPrototype> {
    private ProductPrototype[] productPrototypes;

    @Override
    public boolean add(ProductPrototype productPrototype) {
        int length = productPrototypes.length;
        ProductPrototype[] newProductPrototypes;

        for (int counter = 0; counter < length; counter += 1) {
            if (productPrototypes[counter] == null) {
                productPrototypes[counter] = productPrototype;
                return true;
            }
        }

        newProductPrototypes = new ProductPrototype[length + 1];

        System.arraycopy(productPrototypes, 0, newProductPrototypes, 0, length);
        this.productPrototypes = newProductPrototypes;

        productPrototypes[length] = productPrototype;

        return true;
    }

    @Override
    public boolean remove(String id) {
        for (int counter = 0, length = productPrototypes.length; counter < length; counter += 1) {
            if (productPrototypes[counter] != null && productPrototypes[counter].getId().equalsIgnoreCase(id)) {
                productPrototypes[counter] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public ProductPrototype get(String id) {
        for (ProductPrototype current : productPrototypes) {
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

    public boolean replace(String id, ProductPrototype productPrototype) {
        for (int counter = 0, length = productPrototypes.length; counter < length; counter += 1) {
            if (productPrototypes[counter] != null && productPrototypes[counter].getId().equalsIgnoreCase(id)) {
                productPrototypes[counter] = productPrototype;
                return true;
            }
        }

        return false;
    }

    public ProductPrototypeRepoArray() {
        this.productPrototypes = new ProductPrototype[1];
    }
}