package android.booker.pulse;
import java.io.Serializable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
//
public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    private User_Logged_In userLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__logged__in);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        //testing to see if the parcelable object was passed successfully via intent
        Toast.makeText(getApplicationContext(), "username is " + currentUser.getUserName(), Toast.LENGTH_LONG).show();
    }
}
