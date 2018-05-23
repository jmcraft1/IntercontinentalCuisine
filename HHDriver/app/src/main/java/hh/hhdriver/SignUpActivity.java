package hh.hhdriver;

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

public class SignUpActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword;
    private String user = "";
    private String email;

    public static final String EXTRA_MESSAGE = "hyderabadhouse.hh.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUsername = (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (MaterialEditText) findViewById(R.id.edtConfirmPassword);

        email = edtUsername.getText().toString();



        // gets a reference of the driver table from the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = database.getReference("Driver");
    }

    // when driver hits next button, checks for empty fields
    public void register(View view)
    {

        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter an email address!", Toast.LENGTH_SHORT).show();
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
        else
        {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_customer = database.getReference("Driver");

            final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            table_customer.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(int i = 0; i < edtUsername.getText().toString().length(); i++)
                    {
                        if(!(edtUsername.getText().toString().charAt(i) == '.'))
                        {
                            user += edtUsername.getText().toString().charAt(i);
                        }
                    }

                    // If driver already in database
                    if (dataSnapshot.child(user).exists()) {
                        mDialog.dismiss();
                        alreadyThere();
                    }

                    // If not then go to account activity to continue registering
                    else
                    {
                        mDialog.dismiss();
                        String pass = edtPassword.getText().toString();
                        Intent next = new Intent(SignUpActivity.this,AccountActivity.class);
                        next.putExtra("email", edtUsername.getText().toString());
                        next.putExtra("password", pass);
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

    // lets driver know account already registered
    public void alreadyThere()
    {
        Toast.makeText(this, "Account already registered", Toast.LENGTH_SHORT).show();
    }

}
