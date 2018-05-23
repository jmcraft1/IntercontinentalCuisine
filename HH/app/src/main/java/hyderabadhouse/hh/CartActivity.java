package hyderabadhouse.hh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import hyderabadhouse.hh.Model.Customer;


// This activity shows you what is in your cart and you can either continue shopping or checkout
public class CartActivity extends AppCompatActivity {

    FirebaseDatabase fBase, fBase2;
    DatabaseReference table, table2;
    String user, busName, ord;
    ArrayList<String> foods = new ArrayList<>();
    ArrayList<String> order = new ArrayList<>();
    TextView tvOrd;
    ArrayList<OrderBuilder> ordBuild = new ArrayList<>();
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Gets the intent which called cart activity, in this case it would be the category activity
        // from the menu options, and extract the business name and the email from it.
        Intent intent = getIntent();
        busName = intent.getStringExtra("name");
        user = intent.getStringExtra("email");
        tvOrd = findViewById(R.id.txtOrder);


        // Gets instance of OrderBuilder table from firebase database
        // This is the table that has been building up the items for an order to submit to a store
        fBase = FirebaseDatabase.getInstance();
        table = fBase.getReference("OrderBuilder");

        // Adds a value event listener to the database reference, table.
        table.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // Iterates through all items in order builder table.
                // If the customer attached to each item in the order builder table is the same
                // as the email of the customer passed to this activity, then add that item in
                // the order to an array of OrderBuilders
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderBuilder ob = ds.getValue(OrderBuilder.class);

                    if (ob.getCustomer().equals(user)){
                        ordBuild.add(ob);
                        foods.add(ob.getItem());
                    }
                }

                // Calls test method, which sets the order with price to the Textview
                test();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    // If continue shopping was clicked, put the store name and the customer email in the intent and
    // go back to the category activity
    public void contShop(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("name", busName);
        intent.putExtra("email", user);
        startActivity(intent);
    }


    // If submit order was clicked ...
    public void sendOrder(View view){

        // Gets instance of the customer table in the firebase database
        fBase2 = FirebaseDatabase.getInstance();
        table2 = fBase.getReference("Customer");

        // Adds value event listener to the customer table reference
        table2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Iterate through all the customers in table and find one that is ordering
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Customer c = ds.getValue(Customer.class);

                    // Once found, go to the thanks you screen
                    if (c.getEmail().equals(user)){
                        thanks();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Sends user to the thank you screen, with the store name, the customer email, the order and the
    // price packaged in the intent
    public void thanks(){

        Intent intent = new Intent(this, ThanksActivity.class);
        intent.putExtra("name", busName);
        intent.putExtra("email", user);
        intent.putExtra("order", ord);
        String p = "" + price;
        intent.putExtra("price", p);
        startActivity(intent);

    }

    // This method finds the price of the order and sets the text of the textview to the order and price.
    public void test(){

        ord = "";
        price = 0.0;

        for (int i = 0; i < ordBuild.size(); i++){
            ord += ordBuild.get(i).getQuantity() + " " + ordBuild.get(i).getItem() + ",\n";
            double temp = Double.parseDouble(ordBuild.get(i).getPrice());
            int temp2 = Integer.parseInt(ordBuild.get(i).getQuantity());
            price += temp * temp2;
        }
        price += price * .0925;
        price *= 100;
        if (price - (int)price >= 0.5){
            price++;
        }

        price = (int)price;
        price = price/100.0;
        ord += "Total: " + price;
        tvOrd.setText(ord);
    }
}