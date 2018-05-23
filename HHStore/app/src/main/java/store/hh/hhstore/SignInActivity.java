package store.hh.hhstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

// lets store sign into their account
public class SignInActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    String user;
    public static final String EXTRA_MESSAGE = "store.hh.hhstore.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtUsername = (MaterialEditText)findViewById(R.id.edtName);
    }

    // after user enters email and password and is validated then go to home screen
    public void onSubmit(View view)
    {
        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a business name!", Toast.LENGTH_SHORT).show();
        }
        else if (edtPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a password!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Init Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("Store");




            final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
            mDialog.setMessage("Loading...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){

                    user = edtUsername.getText().toString();


                    //Check if usert exist sin database
                    if (dataSnapshot.child(user).exists()) {

                        //Get user information
                        mDialog.dismiss();
                        Store store = dataSnapshot.child(user).getValue(Store.class);
                        // if passwords match then go to home method
                        if (store.getPassword().equals(edtPassword.getText().toString())) {

                            goToHome();

                        }
                        // passwords don't match
                        else {
                            Toast.makeText(SignInActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    // if user not found in database
                    else {
                        mDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Business not found in database", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    // method to send user to home screen
    public void goToHome()
    {
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        intent.putExtra("busName", user);
        startActivity(intent);
    }
}
