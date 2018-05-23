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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hyderabadhouse.hh.Model.Customer;

public class AccountActivity extends AppCompatActivity {

    EditText edtPhone, edtfirstName, edtlastName, edtAddress, edtCity, edtState, edtZip;
    String userName, pass, message, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        //Gets the intent that called account activity. This activity can only be called from register activity.
        Intent intent = getIntent();

        //String message gets the string passed from register activity. This is the user name (email without the .)
        //password, and full email
        message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);

        //Extract userName, password, and email from message.
        userName = message.substring(0, message.indexOf("password_begins", 0));
        pass = message.substring((15 + message.indexOf("password_begins", 0)), message.indexOf("email_begins", 0));
        email = message.substring(12 + message.indexOf("email_begins", 0));

        //Get edit texts to hold user account input
        edtfirstName = (MaterialEditText) findViewById(R.id.edtfirstName);
        edtlastName = (MaterialEditText) findViewById(R.id.edtlastName);
        edtAddress = (MaterialEditText) findViewById(R.id.edtAddress);
        edtCity = (MaterialEditText) findViewById(R.id.edtCity);
        edtState = (MaterialEditText) findViewById(R.id.edtState);
        edtZip = (MaterialEditText) findViewById(R.id.edtZip);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
    }


    //When submit is clicked
    public void onSubmit(View view)
    {


        //If else block to see if all fields have been entered
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
        else if(!validPhone(edtPhone.getText().toString()))
        {
            Toast.makeText(this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
        }
        else
        {

            //Initializes firebase database and gets Customer table reference.
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_customer = database.getReference("Customer");

            final ProgressDialog mDialog = new ProgressDialog(AccountActivity.this);
            mDialog.setMessage("Please wait one moment");
            mDialog.show();

            //Any change in Customer table will be picked up by ValueEventListener
            table_customer.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //Creates customer object to store in customer table in firebase database
                    Customer customer = new Customer(edtfirstName.getText().toString(),
                            edtlastName.getText().toString(),
                            edtAddress.getText().toString(),
                            edtCity.getText().toString(),
                            edtState.getText().toString(),
                            edtZip.getText().toString(),
                            edtPhone.getText().toString(), pass, email);
                    mDialog.dismiss();


                    //Customer added to customer table
                    table_customer.child(userName).setValue(customer);

                    //Calls registered method to go to next activity
                    registered();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    public void registered()
    {

        //Lets user know their account has been registered and calls ChooseBusinessActivity with
        //customer email attached.
        Toast.makeText(this, "Account has been registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChooseBusinessActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }


    // Method to check for a valid phone number in the form xxx-xxx-xxxx
    // When called it always return true. Just uncomment to use and comment out the return true statement
    public boolean validPhone(String phNum)
    {
        return true;

        /*
        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(phNum);

        if (matcher.matches()) {
            return true;
        }
        else
        {
            return false;
        }
        */

    }
}
