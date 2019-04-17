package android.booker.pulse;
import java.io.Serializable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    private ImageView gearIcon;
    private ImageView userIcon;
    private ImageButton unlockButton;
    private ImageButton sendCodeButton;
    private User_Logged_In userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__logged__in);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        gearIcon = findViewById(R.id.gearIcon);
        userIcon = findViewById(R.id.userIcon);
        unlockButton = findViewById(R.id.unlockButton);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        //testing to see if the user object was passed successfully via intent
        Toast.makeText(getApplicationContext(), "username is " + currentUser.getUserName(), Toast.LENGTH_LONG).show();

        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(userIcon);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(gearIcon);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.pulse))
                .into(unlockButton);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.send))
                .into(sendCodeButton);
    }
}
