package store.hh.hhstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by coven on 5/1/2018.
 */

// This adapter class shows how each card in the order screen behaves
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context c;
    private ArrayList<Orders> list;
    private String busName, user;
    private TextView tvOrd;



    public OrderAdapter(Context c, ArrayList<Orders> list, String busName) {
        this.c = c;
        this.list = list;
        this.busName = busName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.order_layout, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    // each card is an order from a list of orders. this binds the order to each card
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Orders o = list.get(position);
        tvOrd = holder.tvOrder;
        String sendToStore = o.getOrder() + "\n" + o.getTotal() + "\n" + o.getName() + "\n" + o.getAddress() + "\n" +  o.getPhone() + "\n" + o.getAccepted();
        tvOrd.setText(sendToStore);


    }

    // returns number of orders in list
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrder;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrder = itemView.findViewById(R.id.price);
        }
    }
}
