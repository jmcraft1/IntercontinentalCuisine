package hyderabadhouse.hh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword;
    private String user = "";
    private String email;

    public static final String EXTRA_MESSAGE = "hyderabadhouse.hh.MESSAGE";

    // This screen has three text boxes for user to enter email, password, and confirm password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (MaterialEditText) findViewById(R.id.edtConfirmPassword);

        email = edtUsername.getText().toString();



        // Gets database reference of the customer table
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = database.getReference("Customer");
    }

    public void register(View view)
    {
        Emailvalidator ev = new Emailvalidator();

        // If email is blank ...
        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter an email address!", Toast.LENGTH_SHORT).show();
        }

        // If invalid email address, meaning no @ or no .
        else if(!ev.validateEmail(edtUsername.getText().toString()))
        {
            Toast.makeText(this, "You entered an invalid email address!", Toast.LENGTH_SHORT).show();
        }

        // If password is blank ...
        else if (edtPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a password!", Toast.LENGTH_SHORT).show();
        }

        // If cofirm password is blank ...
        else if(edtConfirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please confirm password!", Toast.LENGTH_SHORT).show();
        }

        // If password and cofirm password do not match ...
        else if(!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString()))
        {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }

        // otherwise go here
        else
        {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_customer = database.getReference("Customer");

            final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            table_customer.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Taking out the . in the email to check to see if user already exists
                    for(int i = 0; i < edtUsername.getText().toString().length(); i++)
                    {
                        if(!(edtUsername.getText().toString().charAt(i) == '.'))
                        {
                            user += edtUsername.getText().toString().charAt(i);
                        }
                    }

                    // If user already in customer table let them know with a toast in the alreadyThere method
                    if (dataSnapshot.child(user).exists()) {
                        mDialog.dismiss();
                        alreadyThere();
                    }

                    // If not, go to the account activity to continue registering.
                    // Send userName (email with no .), password, and user real email address to next intent
                    else
                    {
                        mDialog.dismiss();
                        String pass = edtPassword.getText().toString();
                        String message = user + "password_begins" + pass + "email_begins" + edtUsername.getText().toString();
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

    public void alreadyThere()
    {
        Toast.makeText(this, "Account already registered", Toast.LENGTH_SHORT).show();
    }


}
