package com.tsystems.stand.bean.jsf;

import com.tsystems.stand.bean.ejb.TopProducts;
import com.tsystems.stand.model.Product;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Backing bean. It has only one method which should return top products list.
 */
@ManagedBean
@SessionScoped
public class ProductList implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Injected bean which stores top products values.
     * See {@link TopProducts}
     */
    @EJB
    private TopProducts topProducts;

    /**
     * Simple getter.
     * @return tops that the application stores.
     */
    public List<Product> getTopProducts() {
        return topProducts.getTops();
    }
}
