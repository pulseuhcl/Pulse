package android.booker.pulse;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    Button profileButton;


    private User_Logged_In userLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);
        // receive the User object from the previous view
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        //testing to see if the parcelable object was passed successfully via intent
        //Toast.makeText(getApplicationContext(), "first name is " + currentUser.getFirstName(), Toast.LENGTH_LONG).show();
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(ProfileButtonListener);
    }

    // event listeners here
    private View.OnClickListener ProfileButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            ProfileButtonClicked();
        }
    };

    private void ProfileButtonClicked(){
        Intent intent = new Intent(User_Logged_In.this, Profile_Page.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}
