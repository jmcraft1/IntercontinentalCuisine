package store.hh.hhstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

// Activity to add item to database
public class AddItemActivity extends AppCompatActivity {

    private EditText edtItemName, edtPrice, edtCat;
    String itemName, price, business, cat, business2;

    public static final String busName = "store.hh.hhstore.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Extracts business name from activity that called this one
        Intent intent = getIntent();
        business = intent.getStringExtra("busName");


    }

    // When submit is hit, information from user about item, name and price, is taken from fields
    // and stored in the items table of the database, then puts the business name in the intent to
    // go back to the home screen
    public void onSubmit(View view)
    {
        edtItemName = (MaterialEditText) findViewById(R.id.edtItemName);
        edtPrice = (MaterialEditText) findViewById(R.id.edtPrice);
        edtCat = (MaterialEditText) findViewById(R.id.edtCat);
        cat = edtCat.getText().toString();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_items = database.getReference("Items");

        Items item = new Items(edtItemName.getText().toString(), edtPrice.getText().toString(),business, edtCat.getText().toString());
        String key = table_items.push().getKey();
        table_items.child(key).setValue(item);

        Intent intent = new Intent(AddItemActivity.this,HomeActivity.class);
        intent.putExtra("busName", business);
        startActivity(intent);
    }
}
