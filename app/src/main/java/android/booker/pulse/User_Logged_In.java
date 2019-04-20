package android.booker.pulse;
        import java.io.Serializable;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
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
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__logged__in);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        gearIcon = findViewById(R.id.gearIcon);
        userIcon = findViewById(R.id.userIcon);
        unlockButton = findViewById(R.id.unlockButton);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profileButtonListener);
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

    private View.OnClickListener profileButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            profileButtonClicked();
        }
    };

    private void profileButtonClicked(){
        Intent intent = new Intent(User_Logged_In.this, Profile_Page.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}

