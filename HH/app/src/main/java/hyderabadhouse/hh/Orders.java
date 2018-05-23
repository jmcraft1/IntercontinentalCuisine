package hyderabadhouse.hh;

/**
 * Created by coven on 4/29/2018.
 */

// Class of Order objects, which is the total order with all the orderbuilder objects included.
public class Orders {

    private String email, address, name, order, phone, store, total, accepted;

    public Orders() {
    }

    // An Orders object contains the customer email, address, name, their order, phone number the store,
    // the total price, and the default of accepted is not yet accepted
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

    public String getTotal() {return total;}

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAccepted() {return accepted;}

    public void setAccepted(String accepted) {this.accepted = accepted;}
}
