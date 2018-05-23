package hh.hhdriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

// screen where driver can change information
public class EditAccountActivity extends AppCompatActivity {

    private String email;
    private String user = "";
    private Driver driver;
    private EditText edtfirstName, edtlastName, edtAddress, edtCity, edtState, edtZip, edtPhone, edtEmail;


    // gets a reference of the driver table in the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference table_customer = database.getReference("Driver");

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

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // takes out . in email so we can find in driver table
        for(int i = 0; i < email.length(); i++)
        {
            if(!(email.charAt(i) == '.'))
            {
                user += email.charAt(i);
            }
        }

        table_customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                driver = dataSnapshot.child(user).getValue(Driver.class);

                TextView fname = findViewById(R.id.edtfirstName);
                fname.setText(driver.getFirstName());
                TextView lname = findViewById(R.id.edtlastName);
                lname.setText(driver.getLastName());
                TextView addy = findViewById(R.id.edtAddress);
                addy.setText(driver.getAddress());
                TextView city = findViewById(R.id.edtCity);
                city.setText(driver.getCity());
                TextView state = findViewById(R.id.edtState);
                state.setText(driver.getState());
                TextView zip = findViewById(R.id.edtZip);
                zip.setText(driver.getZip());
                TextView phone = findViewById(R.id.edtPhone);
                phone.setText(driver.getPhone());
                TextView email = findViewById(R.id.edtEmail);
                email.setText(driver.getEmail());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // when driver clicks on submit anything different gets changed in the database
    public void onSub(View view){

        table_customer.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!driver.getFirstName().equals(edtfirstName.getText().toString()))
                {
                    driver.setFirstName(edtfirstName.getText().toString());
                    table_customer.child(user).child("firstName").setValue(edtfirstName.getText().toString());
                }
                if (!driver.getLastName().equals(edtlastName.getText().toString()))
                {
                    driver.setLastName(edtlastName.getText().toString());
                    table_customer.child(user).child("lastName").setValue(edtlastName.getText().toString());
                }
                if (!driver.getAddress().equals(edtAddress.getText().toString()))
                {
                    driver.setAddress(edtAddress.getText().toString());
                    table_customer.child(user).child("address").setValue(edtAddress.getText().toString());
                }
                if (!driver.getCity().equals(edtCity.getText().toString()))
                {
                    driver.setCity(edtCity.getText().toString());
                    table_customer.child(user).child("city").setValue(edtCity.getText().toString());
                }
                if (!driver.getState().equals(edtState.getText().toString()))
                {
                    driver.setState(edtState.getText().toString());
                    table_customer.child(user).child("state").setValue(edtState.getText().toString());
                }
                if (!driver.getZip().equals(edtZip.getText().toString()))
                {
                    driver.setZip(edtZip.getText().toString());
                    table_customer.child(user).child("zip").setValue(edtZip.getText().toString());
                }
                if (!driver.getPhone().equals(edtPhone.getText().toString()))
                {
                    driver.setPhone(edtPhone.getText().toString());
                    table_customer.child(user).child("phone").setValue(edtPhone.getText().toString());
                }
                if (!driver.getEmail().equals(edtEmail.getText().toString()))
                {
                    driver.setEmail(edtEmail.getText().toString());
                    table_customer.child(user).child("email").setValue(edtEmail.getText().toString());
                }

                // then go to the home activity
                Intent intent = new Intent(EditAccountActivity.this, HomeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
