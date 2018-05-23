package hyderabadhouse.hh;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;

/**
 * Created by coven on 4/25/2018.
 */

// This adapter goes with the Category Activity. This is what is applied to each card in the
// recycler view of the Category Activity.
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>{

    private Context c;
    private ArrayList<String> list;
    private String busName, user;

    public CatAdapter(Context c, ArrayList<String> list, String busName, String user) {
        this.c = c;
        this.list = list;
        this.busName = busName;
        this.user = user;
    }

    // cat_card_layout is the xml associated with this viewholder for each card
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.cat_card_layout, parent, false);
        CatAdapter.ViewHolder viewHolder = new CatAdapter.ViewHolder(view);
        return viewHolder;
    }

    // List is a list of the categories of the store, so each card gets a button with the text set
    // to a different category of the store.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final String s = list.get(position);
        Button but = holder.b;
        but.setText(s);

        // Each button also gets an Onclicklistener, so when clicked the Items List Activity is called
        // and the store name, the category chosen, and the user email is sent to this activity.
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (c, ItemListActivity.class);
                intent.putExtra("name", busName);
                intent.putExtra("category", s);
                intent.putExtra("email", user);
                c.startActivity(intent);
            }
        });

    }

    // This method returns the size of the list, which is the list of categories, so the recycler knows
    // how many cards will be there.
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button b;

        public ViewHolder(View itemView) {
            super(itemView);
            b = (Button) itemView.findViewById(R.id.btn2);
        }
    }
}
