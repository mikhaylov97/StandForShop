package com.tsystems.stand.jsf;

import com.tsystems.stand.ejb.TopProductsBean;
import com.tsystems.stand.model.Product;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class ProductsBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @EJB
    private TopProductsBean topProductsBean;

   // private ArrayList<Product> topProducts;

    private List<Product> getTopProductsBean() {
        return topProductsBean.getTops();
    }

    public List<Product> getTopProducts() {
        //topProducts = topProductsBean.getTops();
        return topProductsBean.getTops();
    }

    public String refresh() {
        return "products";
    }
}
