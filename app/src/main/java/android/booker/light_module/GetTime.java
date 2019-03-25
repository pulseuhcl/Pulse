package android.booker.light_module;

import java.util.Calendar;

public class GetTime {
    private Calendar c = Calendar.getInstance();
    private int currentSecond;

    public int GetCurrentSecond(){
        currentSecond = c.get(Calendar.SECOND);
        return currentSecond;
    }

}


