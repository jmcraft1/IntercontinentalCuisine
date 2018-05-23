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

public class ItemListActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Items> items = new ArrayList<>();
    ItemAdapter adapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase fBase;
    DatabaseReference table;
    String busName, category, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Get the business name, the category selected, and the email of the customer from the intent
        //  that called this one
        Intent intent = getIntent();
        busName = intent.getStringExtra("name");
        category = intent.getStringExtra("category");
        user = intent.getStringExtra("email");

        // Gets reference of the items table
        fBase = FirebaseDatabase.getInstance();
        table = fBase.getReference("Items");
        items.clear();

        // add value event listener for this items table reference
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items.clear();

                // Iterate through the items in the table and add the item to an array of items
                int child = (int)dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Items s = ds.getValue(Items.class);
                    items.add(s);

                    // When all items have been iterated through, go to the choose method
                    if(items.size() == child){
                        choose();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void choose(){
        ArrayList<Items> cats = new ArrayList<>();

        // Iterate through all the items and ...
        for (int i = 0; i < items.size(); i++){

            // If the store of the item equals the busname sent from the previous intent and ...
            if (items.get(i).getStore().equals(busName)){

                // If the category of the item equals the category sent from the previous intent
                // then add to a string array of categories called cats
                if(items.get(i).getCategory().equals(category)){

                    cats.add(items.get(i));

                }
            }
        }

        // Create adapter for the cards, with the catagory array, cats
        rv = findViewById(R.id.itemRecView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(this, cats, user);
        rv.setAdapter(adapter);
    }
}
