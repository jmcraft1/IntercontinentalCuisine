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

public class LogInActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private String user = "";
    private String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtUsername = (MaterialEditText)findViewById(R.id.edtUserName);
    }


    // when driver hits submit, checks for empty fields
    public void onSubmit(View view){

        if (edtUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a user name!", Toast.LENGTH_SHORT).show();
        }
        else if (edtPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a password!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Init Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("Driver");




            final ProgressDialog mDialog = new ProgressDialog(LogInActivity.this);
            mDialog.setMessage("Loading...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){


                    // take out . in email to find in driver table
                    for(int i = 0; i < edtUsername.getText().toString().length(); i++)
                    {
                        if(!(edtUsername.getText().toString().charAt(i) == '.'))
                        {
                            user += edtUsername.getText().toString().charAt(i);
                        }
                    }


                    //Check if user exists in database
                    if (dataSnapshot.child(user).exists()) {

                        //Get user information
                        mDialog.dismiss();
                        Driver driver = dataSnapshot.child(user).getValue(Driver.class);
                        if (driver.getPassword().equals(edtPassword.getText().toString())) {

                            goToHome();

                        } else {
                            Toast.makeText(LogInActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }

                    } else {
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

    // takes driver to home screen
    public void goToHome()
    {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        intent.putExtra("email", edtUsername.getText().toString());
        startActivity(intent);
        //finish();
    }
}
