package hyderabadhouse.hh;

import java.util.ArrayList;

import hyderabadhouse.hh.Model.Customer;

/**
 * Created by coven on 4/26/2018.
 */

// Class of OrderBuilders, which is each item that the customer selects when clicking item button
public class OrderBuilder {

    private String customer;
    private String item;
    private String store;
    private String quantity;
    private String price;

    public OrderBuilder() {
    }

    // An order builder object contains the customer email, the item name, the quantity of the item,
    // the price of the item, and the store from where it was selected
    public OrderBuilder(String customer, String item, String quantity, String price, String store) {
        this.customer = customer;
        this.item = item;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
