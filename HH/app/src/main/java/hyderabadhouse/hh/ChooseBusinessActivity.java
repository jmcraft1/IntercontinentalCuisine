package hyderabadhouse.hh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseBusinessActivity extends AppCompatActivity{

    RecyclerView rv;
    ArrayList<Store> stores = new ArrayList<>();
    MyAdapter adapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase fBase;
    DatabaseReference table;
    String user;

    // This intent lists all the business registered in the database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_business);

        // Get the intent that called this one and extract the customer email.
        Intent intent = getIntent();
        user = intent.getStringExtra("email");

        // Get firebase database reference for the store table
        fBase = FirebaseDatabase.getInstance();
        table = fBase.getReference("Store");
        stores.clear();

        // Add value event listener for the store table reference.
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stores.clear();
                int child = (int)dataSnapshot.getChildrenCount();

                // Iterate through the registered stores and add them to a Stores arraylist
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Store s = ds.getValue(Store.class);
                    stores.add(s);

                    // When all stores have been added go to choose method
                    if(stores.size() == child){
                        choose();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // This method creates the adapter with a list of stores to populate the recycler view
    public void choose(){

        rv = findViewById(R.id.recView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new MyAdapter(this, stores, user);
        rv.setAdapter(adapter);
    }
}
