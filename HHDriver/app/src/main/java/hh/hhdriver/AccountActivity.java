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

public class AccountActivity extends AppCompatActivity {

    EditText edtPhone, edtfirstName, edtlastName, edtAddress, edtCity, edtState, edtZip;
    String pass, email;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        pass = intent.getStringExtra("password");
        email = intent.getStringExtra("email");
        edtfirstName = (MaterialEditText) findViewById(R.id.edtfirstName);
        edtlastName = (MaterialEditText) findViewById(R.id.edtlastName);
        edtAddress = (MaterialEditText) findViewById(R.id.edtAddress);
        edtCity = (MaterialEditText) findViewById(R.id.edtCity);
        edtState = (MaterialEditText) findViewById(R.id.edtState);
        edtZip = (MaterialEditText) findViewById(R.id.edtZip);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
    }

    // collects all user information and checks for empty fields
    public void onSubmit(View view){

        if (edtfirstName.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a first name!", Toast.LENGTH_SHORT).show();
        }
        else if (edtlastName.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You did not enter a last name!", Toast.LENGTH_SHORT).show();
        }
        else if(edtAddress.getText().toString().isEmpty())
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
        else
        {
            // driver registers his email without the . for the primary key in the driver table in the database
            for(int i = 0; i < email.length(); i++)
            {
                if(!(email.charAt(i) == '.'))
                {
                    key += email.charAt(i);
                }
            }

            // get reference for the driver table in the database
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table = database.getReference("Driver");

            final ProgressDialog mDialog = new ProgressDialog(AccountActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            table.addValueEventListener(new ValueEventListener()
            {

                // adds driver to the driver table, thus registering, then go to registered method
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Driver driver = new Driver(edtfirstName.getText().toString(),
                            edtlastName.getText().toString(),
                            edtAddress.getText().toString(),
                            edtCity.getText().toString(),
                            edtState.getText().toString(),
                            edtZip.getText().toString(),
                            edtPhone.getText().toString(), email, pass);
                    mDialog.dismiss();

                    table.child(key).setValue(driver);
                    registered();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    // lets driver know they are registered, then go to home screen
    public void registered()
    {
        Toast.makeText(this, "Account has been registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
