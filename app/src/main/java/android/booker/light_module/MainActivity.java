package android.booker.light_module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button flashButton;
    private EditText frequencyInput;
    private GetTime timeObject;

    // Event listener for FlashButton
    private View.OnClickListener FlashButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FlashButtonClicked();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frequencyInput = findViewById(R.id.frequencyInputEditText);
        flashButton = findViewById(R.id.FlashButton);
        flashButton.setOnClickListener(FlashButtonOnClickListener);
    }

    private void FlashButtonClicked(){
        // gets the frequency that the user input into the EditText box
        int frequency = Integer.parseInt(frequencyInput.getText().toString());
        // gets the time one second ahead of the start time
        int stopTime = timeObject.GetCurrentSecond() + 1;

        while(frequency > 0){
            if(stopTime != timeObject.GetCurrentSecond()){

                frequency--;
            }

        }


    }
}
