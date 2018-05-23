package hyderabadhouse.hh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hyderabadhouse.hh.Model.Customer;

// This screen thanks the customer, gives them their order and price total. A customer can select to
// place another order with a different or same store or an option to sign out
public class ThanksActivity extends AppCompatActivity {

    TextView t;
    String email, name, address, order, phone, price, store, key;
    FirebaseDatabase fBase;
    DatabaseReference table;

    // Display thank you and order for customer upon entering this screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        t = findViewById(R.id.thanks);
        String s = "Thank you for your order, it should arrive shortly.";
        t.setText(s);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        store = intent.getStringExtra("name");
        order = intent.getStringExtra("order");
        price = intent.getStringExtra("price");

        // Gets a database reference of the customer table
        fBase = FirebaseDatabase.getInstance();
        table = fBase.getReference("Customer");

        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Iterates through the customers, finds the one that matches the email sent to this activity
                // which is the email of the user sending the order, then go to the sending method
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Customer c = ds.getValue(Customer.class);
                   if (c.getEmail().equals(email)){
                       name = c.getFirstName() + " " + c.getLastName();
                       address = c.getAddress();
                       phone = c.getPhone();
                       sending();
                   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // This method inserts the order in the Orders table of the database
    public void sending(){

        // Gets a reference of the Orders table in the database
        table = fBase.getReference("Orders");
        table.addListenerForSingleValueEvent(new ValueEventListener() {

            // Inserts a new Order in the order table
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Orders orders = new Orders(email, address, name, order, phone, store, price, "not yet accepted");
                String key = table.push().getKey();
                table.child(key).setValue(orders);
                remove();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Removes all items from the orderbuilder table that were just used to create the order
    public void remove(){
        table = fBase.getReference("OrderBuilder");
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    OrderBuilder ob = ds.getValue(OrderBuilder.class);
                    if(ob.getCustomer().equals(email)){

                        key = ds.getKey();
                        table.child(key).child("customer").setValue(null);
                        table.child(key).child("store").setValue(null);
                        table.child(key).child("item").setValue(null);
                        table.child(key).child("price").setValue(null);
                        table.child(key).child("quantity").setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // if customer chooses continue shopping, then go to the chooseBusinessActivity
    public void onContinue(View view){

        Intent intent = new Intent (this, ChooseBusinessActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }

    // If the customer chooses to sign out then go to the very first opening screen
    public void onSignOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void test(){
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
    }
}
