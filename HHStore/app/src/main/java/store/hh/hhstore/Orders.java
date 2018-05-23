package store.hh.hhstore;

/**
 * Created by coven on 5/1/2018.
 */

// Class of Orders objects, consisting of the customer email, address, name, their order, customer phone number,
    // store order is from the total price, and a default value of not yet accepted
public class Orders {

    private String email, address, name, order, phone, store, total, accepted;

    public Orders() {
    }

    public Orders(String email, String address, String name, String order, String phone, String store, String total, String accepted) {
        this.email = email;
        this.address = address;
        this.name = name;
        this.order = order;
        this.phone = phone;
        this.store = store;
        this.total = total;
        this.accepted = accepted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
