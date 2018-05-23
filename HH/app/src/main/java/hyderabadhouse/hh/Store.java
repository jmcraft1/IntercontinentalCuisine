package hyderabadhouse.hh;

/**
 * Created by coven on 4/2/2018.
 */


// Creates Store objects
public class Store {

    private String name, address, city, email, password, phone, state, zip;

    public Store() {
    }

    // Store object consists of the name of the store, the address, the city, email, a password, the
    // phone number, the state, and the zip
    public Store(String name, String address, String city, String email,
                 String password, String phone, String state, String zip) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
