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
 * Created by coven on 4/15/2018.
 */

// This adapter goes with the ChooseBusiness Activity. This is what is applied to each card in the
// recycler view of the Category Activity.
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{


    private Context c;
    private ArrayList<Store> list;
    private String user;

    MyAdapter(Context c, ArrayList<Store> list, String user){
        this.c = c;
        this.list = list;
        this.user = user;
    }

    // card_view_layout is the xml associated with this viewholder for each card
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.card_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // List is a list of the stores, so each card gets a button with the text set
    // to a different store.
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final Store s = list.get(position);
        Button but = holder.b;
        but.setText(s.getName());

        // Each button also gets an Onclicklistener, so when clicked the Category Activity is called
        // and the store name, and the user email is sent to this activity.
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (c, CategoryActivity.class);
                intent.putExtra("name", s.getName());
                intent.putExtra("email", user);
                c.startActivity(intent);
            }
        });
    }

    // This method returns the size of the list, which is the list of stores, so the recycler knows
    // how many cards will be there.
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button b;

        public ViewHolder(View itemView) {
            super(itemView);
            b = (Button) itemView.findViewById(R.id.btn);
        }
    }

}
