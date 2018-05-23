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

// lets user enter email, password, and confirm password to register store
public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword;

    public static final String EXTRA_MESSAGE = "store.hh.hhstore.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (MaterialEditText) findViewById(R.id.edtConfirmPassword);
    }

    // when register is clicked, checks for empty fields, and matching passwords
    public void register(View view)
    {
        Emailvalidator ev = new Emailvalidator();
        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter the name of the business!", Toast.LENGTH_SHORT).show();
        }
        else if (edtPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a password!", Toast.LENGTH_SHORT).show();
        }
        else if(edtConfirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please confirm password!", Toast.LENGTH_SHORT).show();
        }
        else if(!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString()))
        {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }

        // Once all is verified, then a store table database reference is created
        else
        {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_store = database.getReference("Stores");

            final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            table_store.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // If store already in database go to alreadyThere method
                    if (dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                        mDialog.dismiss();
                        alreadyThere();
                    }

                    // If not then go to account activity to continue registering
                    else
                    {
                        mDialog.dismiss();

                        String userName = edtUsername.getText().toString();
                        String pass = edtPassword.getText().toString();
                        String message = userName + "password_begins" + pass;
                        Intent next = new Intent(RegisterActivity.this,AccountActivity.class);
                        next.putExtra(EXTRA_MESSAGE, message);
                        startActivity(next);
                        finish();


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    // lets user know store is already registered
    public void alreadyThere()
    {
        Toast.makeText(this, "Account already registered", Toast.LENGTH_SHORT).show();
    }
}
