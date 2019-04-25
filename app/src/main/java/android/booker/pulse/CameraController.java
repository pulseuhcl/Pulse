package android.booker.pulse;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraController {
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Boolean light = true;
    private CameraManager cameraManager;
    private String cameraID;
    private Boolean startPattern = false;
    private String userPin;

    public CameraController(Context loggedInContext, String userPin){
        this.cameraManager= (CameraManager) loggedInContext.getSystemService(Context.CAMERA_SERVICE);
        this.userPin = userPin;
    }

    public void GetCameraIDList(){
        try{
            cameraID = cameraManager.getCameraIdList()[0];
        }catch(CameraAccessException e){
            e.printStackTrace();
        }
    }

    // turns on the flash
    private void enableTorch() {
        if(light){
            try {
                cameraManager.setTorchMode(cameraID, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            light = false;
        }
    }

    // turns off the flash
    private void disableTorch() {
        if (!light) {
            try {
                cameraManager.setTorchMode(cameraID, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            light = true;
        }
    }

    Thread flasher = new Thread(new Runnable() {
        @Override

        public void run() {
            // pin pulled from the user object; converted to int
            int pin = Integer.parseInt(userPin);
            // pin converted to binary string
            String pinString = Integer.toBinaryString(pin);
            // binary string to array of characters
            char[] bufferArray = pinString.toCharArray();
            // initialize the list to read the char array values into
            final Deque<Character> pattern = new LinkedList<>();
            int counter = 2;
            while(counter > 0){
                for(int i =0; i < bufferArray.length; i++){
                    pattern.addLast(bufferArray[i]);
                }
                while(pattern.size() < 10 + (10 - bufferArray.length)){
                    pattern.addFirst('0');
                    pattern.addLast('0');
                }
                counter--;
            }
            pattern.addFirst('1');
            pattern.addFirst('1');
            pattern.addFirst('1');
            pattern.addLast('0');
            pattern.addLast('1');

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(, pattern.toString(), Toast.LENGTH_LONG).show();
//                }
//            });

            // sum of consecutive ones to give the time for the flash to stay on
            int delayForZeros = 40;// I need to fix it
            int delayForOnes = 40;//
            /////
            startPattern = true;
            Boolean isOn = false;
            long futureTime=System.currentTimeMillis();

            // wake up call
            enableTorch();
            while(System.currentTimeMillis() <= futureTime + 1){}

            disableTorch();
            while(System.currentTimeMillis() <= futureTime + 10){}

            for(Character c : pattern){
                futureTime = System.currentTimeMillis();

                if(c == '1'){
                    //futureTime = System.currentTimeMillis() + delayForOnes;
                    if(!isOn){
                        enableTorch();
                        isOn = true;
                    }
                    while(System.currentTimeMillis() <= futureTime + delayForOnes){

                    }
                }
                else{
                    //futureTime = System.currentTimeMillis() + delayForZeros;
                    if(isOn){
                        disableTorch();
                        isOn = false;
                    }
                    while(System.currentTimeMillis() <= futureTime + delayForZeros){

                    }
                }
            }
            if(isOn){
                disableTorch();
            }
        }
    });

    public void executePattern(){
        executor.execute(flasher);
    }
}
