package hyderabadhouse.hh;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by coven on 4/25/2018.
 */


// Item adapter class using the item_card layout to create cards for each class
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context c;
    private ArrayList<Items> list;
    private String user;



    ItemAdapter(Context c, ArrayList<Items> list, String user){
        this.c = c;
        this.list = list;
        this.user = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.item_card_layout, parent, false);
        ItemAdapter.ViewHolder viewHolder = new ItemAdapter.ViewHolder(view);
        return viewHolder;
    }


    // binds item button, text view with price, and edit text for quantity
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Items s = list.get(position);
        final Button but = holder.b;
        TextView pr = holder.p;
        final EditText num = holder.qty;
        but.setText(s.getName());
        pr.setText(s.getPrice());

        // When item button is clicked ...
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Gets a reference of the OrderBuilder table in the firebase databse
                // This table build an order for the customer as they hit each item button
                FirebaseDatabase fBase = FirebaseDatabase.getInstance();
                DatabaseReference table = fBase.getReference("OrderBuilder");

                String temp;

                // IF no quantity is selected, then the default is quantity is 1
                if(num.getText().toString().isEmpty()){
                    temp = "1";
                }

                // Otherwise get the the quantity entered into the quantity edit text box
                else{
                    temp = num.getText().toString();
                }

                // Add to the orderBuilder table on orderBuilder object with the data of the customer
                // email, the name of the item, the quantity, the price, and the store
                OrderBuilder ob = new OrderBuilder(user, s.getName(), temp, s.getPrice(), s.getStore());
                String key = table.push().getKey();
                table.child(key).setValue(ob);

                // After item button is selected, then go back to the CategoryActivity, with the Store name
                //  and the customer email
                Intent intent = new Intent(c,CategoryActivity.class);
                intent.putExtra("name", s.getStore());
                intent.putExtra("email", user);
                c.startActivity(intent);
            }
        });
    }

    // Returns the size of the items array
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button b;
        TextView p;
        EditText qty;

        public ViewHolder(View itemView) {
            super(itemView);
            b = (Button) itemView.findViewById(R.id.btn_item);
            p = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.howMany);
        }
    }
}
