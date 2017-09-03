package com.tsystems.stand.ejb;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.tsystems.stand.model.Product;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class TopProductsBean implements Serializable {

    private List<Product> tops = new ArrayList<>();

    public TopProductsBean() {
    }

    @PostConstruct
    public void init() {
        updateTopProducts();
    }

    public List<Product> getTops() {
        return tops;
    }

    public void setTops(List<Product> tops) {
        this.tops = tops;
    }

    public void updateTopProducts() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8080/advertising/stand");

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);


        List list = response.getEntity(ArrayList.class);
        parseListOfProducts(list);
    }

    private void parseListOfProducts(List<Map<String, String>> list) {
        List<Product> tops = new ArrayList<>(10);
        for(Map<String, String> product : list) {
            Long id = null;
            String name = null;
            String price = null;
            for(Map.Entry<String, String> attribute : product.entrySet()) {
                if (attribute.getKey().equals("id")) {
                    id = Long.parseLong(attribute.getValue());
                }
                if (attribute.getKey().equals("name")) {
                    name = attribute.getValue();
                }
                if (attribute.getKey().equals("price")) {
                    price = attribute.getValue();
                }
            }
            Product tmp = new Product(id, name, price, name);
            tops.add(tmp);
        }
        this.tops = tops;
    }
}
