package android.booker.pulse.Class_Files;

import java.time.LocalDateTime;
import java.util.List;

/*This class creates the objects that contain information unique to
the user such as a pin, username, or anything else we might
implement at a later date
*/

public class UserInfo {

    private int pin;
    private LocalDateTime time;
    // contains a list of data that will be iterated with a loop
    // to produce the pulses of light in the pattern
    private List patternData;

    // This method accepts pin entry from the user
    //(may be text box or other widget)
    private UserInfo(int pinEntry){
        this.pin = pinEntry;
        // assigns the current datetime to the user object
        this.time = LocalDateTime.now();
    }

    private void Save(){
        // Will save the pin to local storage
        // should we also save the datetime or other
        // data?
    }

}
