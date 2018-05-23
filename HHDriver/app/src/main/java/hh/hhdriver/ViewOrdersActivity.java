package hh.hhdriver;

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


// View orders screen
public class ViewOrdersActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference table;
    private ArrayList<Orders> orders = new ArrayList<>();
    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private OrderAdapter adapter;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // get database reference of the Orders table
        db = FirebaseDatabase.getInstance();
        table = db.getReference("Orders");
        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                orders.clear();

                // iterate through orders and get all that are not yet accepted
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Orders o = ds.getValue(Orders.class);
                    if (o.getAccepted().equals("not yet accepted")){
                        orders.add(o);
                    }
                }
                seeOrders();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // creates adapter for recycler view that consists of orders not yet accepted
    public void seeOrders(){
        rv = findViewById(R.id.orderRecView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(this, orders, email);
        rv.setAdapter(adapter);
    }
}
