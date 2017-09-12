package com.tsystems.stand.bean.ejb;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.tsystems.stand.model.Product;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Current bean stores state of top products.
 * When this application get message from JMS server it will make request
 * to Shop application in order to get Updated top products list.
 */
@Singleton
public class TopProducts implements Serializable {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(TopProducts.class);


    /**
     * Directly, list of the top products which objects is shown in advertising stand.
     */
    private List<Product> tops = new ArrayList<>();

    /**
     * Empty constructor.
     */
    public TopProducts() {
    }

    /**
     * Initializer after starting of the Stand application. It gets top products.
     */
    @PostConstruct
    public void init() {
        updateTopProducts();
        log.info("Application was successfully started and has got current top products list.");
    }

    /**
     * Simple setter.
     * @return top products list.
     */
    public List<Product> getTops() {
        return tops;
    }

    /**
     * Simple setter.
     * @param tops that must be set.
     */
    public void setTops(List<Product> tops) {
        this.tops = tops;
    }

    /**
     * Important method which makes a request to the Shop application in order
     * to update top products list. Method gets answer and try to parse it.
     */
    public void updateTopProducts() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8080/advertising/stand");

        log.info("Connection was opened.");

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        log.info("Stand have got response without any errors.");


        List list = response.getEntity(ArrayList.class);
        try {
            tops = parseListOfProducts(list);
            log.info("Stand application successfully has parsed list of top products and has set it as current.");
        } catch (Exception e) {
            log.info("During the parsing process some critical situation was occurred.", e);
        }
    }

    /**
     * Method parses received list of products from Shop application.
     * @param list with received products.
     * @return parsed List of top products.
     */
    private List<Product> parseListOfProducts(List<Map<String, String>> list) {
        List<Product> tops = new ArrayList<>(10);
        for(Map<String, String> product : list) {
            Long id = null;
            String name = null;
            String price = null;
            String numberOfSales = null;
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
                if (attribute.getKey().equals("numberOfSales")) {
                    numberOfSales = attribute.getValue();
                }
            }
            Product tmp = new Product(id, name, price, "/image/" + id, numberOfSales);
            tops.add(tmp);
        }
        return tops;
    }
}
