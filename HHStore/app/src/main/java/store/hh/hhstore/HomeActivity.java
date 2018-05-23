package store.hh.hhstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// Home screen where store can choose from adding an item, deleting an item, changing price of an item,
// and view the stores orders
public class HomeActivity extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Gets the store name from the calling intent
        Intent intent = getIntent();
        user = intent.getStringExtra("busName");

    }

    // If store clicks on add Item, go to the addItemActivity
    public void addItem(View view){
        Intent intent = new Intent(HomeActivity.this, AddItemActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);


    }

    // If user hits delete item button, go to delete item activity
    public void delItem(View view){
        Intent intent = new Intent(HomeActivity.this, DelItemActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);

    }

    // If user hits change price button, go to change price activity
    public void changePrice(View view){
        Intent intent = new Intent(HomeActivity.this, ChangePriceActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);

    }

    // If user hits view orders button, go to the view orders screen
    public void seeOrders(View view){
        Intent intent = new Intent(HomeActivity.this, ViewOrdersActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);

    }
}
