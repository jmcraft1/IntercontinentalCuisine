package store.hh.hhstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewOrdersActivity extends AppCompatActivity {

    private TextView tv;
    private FirebaseDatabase db;
    private DatabaseReference table;
    private ArrayList<Orders> orders = new ArrayList<>();
    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private OrderAdapter adapter;
    private String business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        Intent intent = getIntent();
        business = intent.getStringExtra("busName");

        // gets database reference of the Orders table
        db = FirebaseDatabase.getInstance();
        table = db.getReference("Orders");
        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                orders.clear();

                // iterates through the orders and finds all the ones that match the business name
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Orders o = ds.getValue(Orders.class);
                    // and adds them to an array of orders
                    if (o.getStore().equals(business)){

                        orders.add(o);

                    }

                }

                // go to the seeOrders method
                seeOrders();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // method to create adapter for each order
    public void seeOrders(){
        rv = findViewById(R.id.orderRecView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(this, orders, business);
        rv.setAdapter(adapter);
    }
}
