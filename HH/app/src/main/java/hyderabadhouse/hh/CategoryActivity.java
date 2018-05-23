package hyderabadhouse.hh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Items> items = new ArrayList<>();
    CatAdapter adapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase fBase;
    DatabaseReference table;
    String busName, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // This activity uses the overflow menu on the toolbar, so it needs a toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the intent that called this activity and extract the store name and the customer email
        Intent intent = getIntent();
        busName = intent.getStringExtra("name");
        user = intent.getStringExtra("email");


        // Gets instance of the Items table in the firebase database
        fBase = FirebaseDatabase.getInstance();
        table = fBase.getReference("Items");
        items.clear();

        // Adds value event listener to the Items table reference.
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items.clear();
                int child = (int)dataSnapshot.getChildrenCount();

                // Iterates through the items in the Item table and adds them to an arraylist of Items
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Items s = ds.getValue(Items.class);
                    items.add(s);

                    // When all have been added, go to the choose method
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

    // Refers the overflow options menu to the menu_home xml.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }


    // If an item in the overflow menu is selected instead of a category.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Go to edit account activity if account is selected from options menu
        if (id == R.id.action_account) {


            Intent intent2 = new Intent(this, EditAccountActivity.class);
            intent2.putExtra("email", user);
            startActivity(intent2);
        }

        // Go to cart activity if cart is selected from options menu
        if (id == R.id.action_cart) {
            Intent intent = new Intent(this,CartActivity.class);
            intent.putExtra("email", user);
            intent.putExtra("name", busName);
            this.startActivity(intent);
            return true;
        }

        // Go to main activity if sign out is selected from options menu
        if (id == R.id.action_signOut) {
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method iterates through the items and finds the ones that are from the store clicked
    // in the previous activity. Of those items, it gets the all the categories offered by the store
    // and adds them to a string arraylist.
    public void choose(){
        ArrayList<String> cats = new ArrayList<>();

        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getStore().equals(busName)){

                if (!cats.contains(items.get(i).getCategory())){
                    cats.add(items.get(i).getCategory());
                }
            }
        }


        // create new adapter for the cards to fill recycler view
        rv = findViewById(R.id.catRecView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new CatAdapter(this, cats, busName, user);
        rv.setAdapter(adapter);
    }
}
