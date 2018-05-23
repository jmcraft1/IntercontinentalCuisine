package hyderabadhouse.hh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


// Opening screen of the app. User can either sign in or register
public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // If user hits the login button go to the login screen
    public void LogIn(View view)
    {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    // If user hits the register button go to the register screen
    public void Register(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}