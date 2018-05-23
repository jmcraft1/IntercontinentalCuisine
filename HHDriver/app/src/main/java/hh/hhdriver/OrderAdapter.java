package hh.hhdriver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by coven on 5/2/2018.
 */


// adapter class for recycler view in view orders activity
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context c;
    private ArrayList<Orders> list;
    private String email, user, key, s;
    private TextView tvOrd;
    private Button accept;


    public OrderAdapter(Context c, ArrayList<Orders> list, String email) {
        this.c = c;
        this.list = list;
        this.email = email;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.order_layout, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds to each card in the view orders screen an order and an accept button
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Orders o = list.get(position);
        tvOrd = holder.tvOrder;
        accept = holder.btn_acc;
        String sendToStore = o.getOrder() + "\n" + o.getTotal() + "\n" + o.getStore() +
                "\n" + o.getName() + "\n" + o.getAddress() +
                "\n" +  o.getPhone() + "\n" + o.getAccepted();
        tvOrd.setText(sendToStore);
        String a = "Accept";
        accept.setText(a);

        // if accept button is clicked, gets reference of the orders table in the database
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase fBase = FirebaseDatabase.getInstance();
                final DatabaseReference table = fBase.getReference("Orders");
                table.addValueEventListener(new ValueEventListener() {

                    // changes status of order to accepted by and the driver email
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            Orders ord = ds.getValue(Orders.class);
                            if (ord.getEmail().equals(o.getEmail()) && ord.getStore().equals(o.getStore())){
                                key = ds.getKey();
                                s = "Accepted by " + email;
                                break;
                            }
                        }
                        table.child(key).child("accepted").setValue(s);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    // returns the number of orders in the list
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrder;
        Button btn_acc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrder = itemView.findViewById(R.id.txtOrder);
            btn_acc = itemView.findViewById(R.id.btn_accept);
        }
    }
}

