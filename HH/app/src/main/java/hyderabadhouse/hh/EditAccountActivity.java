package hyderabadhouse.hh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import hyderabadhouse.hh.Model.Customer;

public class EditAccountActivity extends AppCompatActivity {

    private String userName, busName;
    private String user = "";
    private Customer customer;
    private EditText edtfirstName, edtlastName, edtAddress, edtCity, edtState, edtZip, edtPhone, edtEmail;

    // Gets database reference for the Customer table
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference table_customer = database.getReference("Customer");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);



        edtfirstName = (MaterialEditText) findViewById(R.id.edtfirstName);
        edtlastName = (MaterialEditText) findViewById(R.id.edtlastName);
        edtAddress = (MaterialEditText) findViewById(R.id.edtAddress);
        edtCity = (MaterialEditText) findViewById(R.id.edtCity);
        edtState = (MaterialEditText) findViewById(R.id.edtState);
        edtZip = (MaterialEditText) findViewById(R.id.edtZip);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);

        // Gets the intent that called this one and extracts the customer email and the store name
        Intent intent = getIntent();
        userName = intent.getStringExtra("email");
        busName = intent.getStringExtra("name");

        // Go through the email and take out the dot because firebase database did not allow dot in primary key
        for(int i = 0; i < userName.length(); i++)
        {
            if(!(userName.charAt(i) == '.'))
            {
                user += userName.charAt(i);
            }
        }

        // Add value event listener to customer table reference
        table_customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                // Gets the information of the user in the customer table.
                // Creates a customer object with that information.
                customer = dataSnapshot.child(user).getValue(Customer.class);

                // Set all the textviews of the activity with the customer information
                TextView fname = findViewById(R.id.edtfirstName);
                fname.setText(customer.getFirstName());
                TextView lname = findViewById(R.id.edtlastName);
                lname.setText(customer.getLastName());
                TextView addy = findViewById(R.id.edtAddress);
                addy.setText(customer.getAddress());
                TextView city = findViewById(R.id.edtCity);
                city.setText(customer.getCity());
                TextView state = findViewById(R.id.edtState);
                state.setText(customer.getState());
                TextView zip = findViewById(R.id.edtZip);
                zip.setText(customer.getZip());
                TextView phone = findViewById(R.id.edtPhone);
                phone.setText(customer.getPhone());
                TextView email = findViewById(R.id.edtEmail);
                email.setText(customer.getEmail());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // References overflow options menu with menu_home xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // If account is selected from overflow menu, then package up customer email and store name
        // and send it this activity, so really just restart this activity.
        if (id == R.id.action_account) {

            Intent intent2 = new Intent(EditAccountActivity.this, EditAccountActivity.class);
            intent2.putExtra("email", userName);
            intent2.putExtra("name", busName);
            startActivity(intent2);
        }

        // If cart is selected, then package up customer email and store name and send it ot cart activity
        if (id == R.id.action_cart) {
            Intent intent = new Intent(this,CartActivity.class);
            intent.putExtra("email", userName);
            intent.putExtra("name", busName);
            this.startActivity(intent);
            return true;
        }

        // If sign out is selected, then go to main activity
        if (id == R.id.action_signOut) {
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method is called when submit is clicked to change information of the customer in the database
    public void onSub(View view)
    {

        table_customer.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean validPhone = true;
                boolean validEmail = true;
                Emailvalidator ev = new Emailvalidator();

                // If anything has changed in information fields, then set new value for customer in
                // database.
                if (!customer.getFirstName().equals(edtfirstName.getText().toString()))
                {
                    customer.setFirstName(edtfirstName.getText().toString());
                    table_customer.child(user).child("firstName").setValue(edtfirstName.getText().toString());
                }
                if (!customer.getLastName().equals(edtlastName.getText().toString()))
                {
                    customer.setLastName(edtlastName.getText().toString());
                    table_customer.child(user).child("lastName").setValue(edtlastName.getText().toString());
                }
                if (!customer.getAddress().equals(edtAddress.getText().toString()))
                {
                    customer.setAddress(edtAddress.getText().toString());
                    table_customer.child(user).child("address").setValue(edtAddress.getText().toString());
                }
                if (!customer.getCity().equals(edtCity.getText().toString()))
                {
                    customer.setCity(edtCity.getText().toString());
                    table_customer.child(user).child("city").setValue(edtCity.getText().toString());
                }
                if (!customer.getState().equals(edtState.getText().toString()))
                {
                    customer.setState(edtState.getText().toString());
                    table_customer.child(user).child("state").setValue(edtState.getText().toString());
                }
                if (!customer.getZip().equals(edtZip.getText().toString()))
                {
                    customer.setZip(edtZip.getText().toString());
                    table_customer.child(user).child("zip").setValue(edtZip.getText().toString());
                }
                if (!customer.getPhone().equals(edtPhone.getText().toString()))
                {

                    // Checks for a valid phone number in the form xxx-xxx-xxxx
                    if(!isValidPhone(edtPhone.getText().toString()))
                    {
                        validPhone = false;
                    }
                    else
                    {
                        validPhone = true;
                        customer.setPhone(edtPhone.getText().toString());
                        table_customer.child(user).child("phone").setValue(edtPhone.getText().toString());
                    }
                }
                if (!customer.getEmail().equals(edtEmail.getText().toString()))
                {

                    // Checks for a valid email via emailvalidator object. Must have @ and .some extension
                    // Uncomment for toast on invalid email
                    if (!ev.validateEmail(edtEmail.getText().toString()))
                    {
                        validEmail = false;
                        //wrongEmail();
                    }
                    else
                    {
                        validEmail = true;
                        customer.setEmail(edtEmail.getText().toString());
                        table_customer.child(user).child("email").setValue(edtEmail.getText().toString());
                        wrongEmail();
                    }
                }

                // If both phone number and email are valid, then package up user's email into the intent
                // for the next activity, and start Choose business activity, where user can choose
                // business to order from.
                if (validPhone && validEmail)
                {

                    Intent intent = new Intent(EditAccountActivity.this, ChooseBusinessActivity.class);
                    intent.putExtra("email", userName);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Uncomment to show toast for invalid email. All emails are valid as of now
    public void wrongEmail()
    {
        //Toast.makeText(this, "You entered an invalid email address!", Toast.LENGTH_SHORT).show();
    }

    // Uncomment to check for a valid phone number in the form xxx-xxx-xxxx
    public boolean isValidPhone(String phNum)
    {

        /*
        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(phNum);

        if (matcher.matches()) {
            return true;
        }
        else
        {
            Toast.makeText(this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        */

        return true;
    }
}
