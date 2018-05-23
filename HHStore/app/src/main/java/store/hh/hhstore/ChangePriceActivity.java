package store.hh.hhstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Allows store to change price of item
public class ChangePriceActivity extends AppCompatActivity {

    EditText edtName, edtPrice;
    FirebaseDatabase database;
    DatabaseReference table;
    String user, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);

        edtName = findViewById(R.id.txtItemName);
        edtPrice = findViewById(R.id.txtPrice);

        // Gets the business name from the intent that called this one
        Intent intent = getIntent();
        user = intent.getStringExtra("busName");
    }

    // When store hits submit to change price button
    public void change(View view){

        // reference for the items table is created
        database = FirebaseDatabase.getInstance();
        table = database.getReference("Items");
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Iterates through all items and finds one that has same store and name as entered by user
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Items item = ds.getValue(Items.class);

                    // Once found the item price is updated in the database and the goHome method is called
                    if(item.getStore().equals(user) && item.getName().equals(edtName.getText().toString())){

                        key = ds.getKey();
                        table.child(key).child("price").setValue(edtPrice.getText().toString());
                        goHome();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    // this method goes back to the home screen
    public void goHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);
    }
}
