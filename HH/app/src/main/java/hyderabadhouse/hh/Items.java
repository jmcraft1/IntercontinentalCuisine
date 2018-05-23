package hyderabadhouse.hh;

/**
 * Created by coven on 4/22/2018.
 */

// Class of Items
public class Items {

    private String name, price, store, category;

    public Items() {
    }

    public Items(String name, String price, String store, String category) {
        this.name = name;
        this.price = price;
        this.store = store;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
