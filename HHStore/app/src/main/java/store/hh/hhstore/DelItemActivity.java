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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

// This screen deletes an item from the database
public class DelItemActivity extends AppCompatActivity {

    private EditText edtItemName;
    String business, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_item);

        // Get the store name from the calling intent
        Intent intent = getIntent();
        business = intent.getStringExtra("busName");
    }

    // When store hits delete button it removes item from database
    public void onDelete(View view)
    {
        edtItemName = (MaterialEditText) findViewById(R.id.edtItemName);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_items = database.getReference("Items");

        table_items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Items item = ds.getValue(Items.class);
                    if(item.getStore().equals(business) && item.getName().equals(edtItemName.getText().toString())){

                        key = ds.getKey();
                        table_items.child(key).child("name").setValue(null);
                        table_items.child(key).child("store").setValue(null);
                        table_items.child(key).child("category").setValue(null);
                        table_items.child(key).child("price").setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Goes back to the home screen
        Intent intent = new Intent(DelItemActivity.this,HomeActivity.class);
        intent.putExtra("busName", business);
        startActivity(intent);

    }
}
