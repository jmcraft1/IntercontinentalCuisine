package hh.hhdriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// home screen where driver can view orders or go to their account
public class HomeActivity extends AppCompatActivity {

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
    }

    public void seeOrders(View view){
        Intent intent = new Intent(this, ViewOrdersActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }

    public void goToAccount(View view){

        Intent intent = new Intent(this, EditAccountActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);



    }
}
