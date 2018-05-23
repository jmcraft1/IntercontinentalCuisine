package store.hh.hhstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AccountActivity extends AppCompatActivity {

    EditText edtPhone, edtAddress, edtCity, edtState, edtZip, edtEmail;
    String userName, pass, message;

    // Gets all relevant store information to create account and store in database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        userName = message.substring(0, message.indexOf("password_begins", 0));
        pass = message.substring((15 + message.indexOf("password_begins", 0)));
        edtAddress = (MaterialEditText) findViewById(R.id.edtAddress);
        edtCity = (MaterialEditText) findViewById(R.id.edtCity);
        edtState = (MaterialEditText) findViewById(R.id.edtState);
        edtZip = (MaterialEditText) findViewById(R.id.edtZip);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
    }

    // When store owner hits submit button, then validates email and checks all fields are entered
    public void onSubmit(View view)
    {

        Emailvalidator ev = new Emailvalidator();
       if(edtAddress.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter an address!", Toast.LENGTH_SHORT).show();
        }
        else if (edtCity.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a city!", Toast.LENGTH_SHORT).show();
        }
        else if(edtState.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a state!", Toast.LENGTH_SHORT).show();
        }
        else if(edtZip.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a zip code!", Toast.LENGTH_SHORT).show();
        }
        else if (edtPhone.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a phone number!", Toast.LENGTH_SHORT).show();
        }
        else if(edtEmail.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter an email address!", Toast.LENGTH_SHORT).show();
        }
        else if(!ev.validateEmail(edtEmail.getText().toString()))
        {
            Toast.makeText(this, "You entered an invalid email address!", Toast.LENGTH_SHORT).show();
        }
        else
        {

            // Gets a reference of the Store table in the database
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_store = database.getReference("Store");

            final ProgressDialog mDialog = new ProgressDialog(AccountActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            table_store.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Creates new store object with data entered by user
                    Store store = new Store(pass,
                            edtAddress.getText().toString(),
                            edtCity.getText().toString(),
                            edtState.getText().toString(),
                            edtZip.getText().toString(),
                            edtPhone.getText().toString(),
                            edtEmail.getText().toString(), userName);
                    mDialog.dismiss();

                    // Adds store object to database
                    table_store.child(userName).setValue(store);
                    registered();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    // Lets user know stored has been registered and go to home screen
    public void registered()
    {
        Toast.makeText(this, "Account has been registered", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("busName", userName);
        startActivity(intent);
    }
}
