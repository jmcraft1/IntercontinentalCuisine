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

import hyderabadhouse.hh.Model.Customer;

public class LogInActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private String user = "";

    // Activity to enter email and password to enter app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtUsername = (MaterialEditText)findViewById(R.id.edtUserName);

    }

    public void onSubmit(View view)
    {

        // Checks to see if an emaiol and password have been entered
        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter an email address!", Toast.LENGTH_SHORT).show();
        }
        else if (edtPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a password!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Init Firebase and gets reference of the customer table
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("Customer");




            final ProgressDialog mDialog = new ProgressDialog(LogInActivity.this);
            mDialog.setMessage("Loading...");
            mDialog.show();

            // Adds value event listener to the customer table reference
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){


                    // Firebase didn't allow the . in the email address to be in the primary key, so
                    // this is the loop that takes out the .
                    for(int i = 0; i < edtUsername.getText().toString().length(); i++)
                    {
                        if(!(edtUsername.getText().toString().charAt(i) == '.'))
                        {
                            user += edtUsername.getText().toString().charAt(i);
                        }
                    }


                    //If user exists in database ...
                    if (dataSnapshot.child(user).exists()) {

                        //Get user information
                        mDialog.dismiss();
                        Customer customer = dataSnapshot.child(user).getValue(Customer.class);

                        // If password entered matches password in database for the email entered then
                        // go to the goHome method
                        if (customer.getPassword().equals(edtPassword.getText().toString())) {

                            goToHome();

                        }

                        // If passwords don't match
                        else {
                            Toast.makeText(LogInActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    // If User does not exist in database
                    else {
                        mDialog.dismiss();
                        Toast.makeText(LogInActivity.this, "User not found in database", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    // This method lets the user go the chooseBusinessActivity, where the user can choose a store to
    // order from
    public void goToHome()
    {
        Intent intent = new Intent(LogInActivity.this, ChooseBusinessActivity.class);
        intent.putExtra("email", edtUsername.getText().toString());
        startActivity(intent);
        //finish();
    }
}
