package android.booker.pulse;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncSetPin extends AsyncTask<String, String, String> {
    private Settings_Page settings_page;
    HttpURLConnection conn;
    URL url = null;
    User currentUser = new User();
    String newPin;

    public AsyncSetPin(Settings_Page settings_page){
        this.settings_page = settings_page;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // optional progress bar would go here
    }

    @Override
    protected String doInBackground(String... params){
        try{
            url = new URL("http://ed11e0fa.ngrok.io/pulse_api/setPin.php");
        }catch(MalformedURLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "exception";
        }
        try{
            // Setup HttpUrlConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");

            // setDoInput and set DoOutput method depict handling of both send/receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", params[0])
                    .appendQueryParameter("password", params[1])
                    .appendQueryParameter("currentPin", params[2])
                    .appendQueryParameter("newPin", params[3]);

            String query = builder.build().getEncodedQuery();
            currentUser.setUserName(params[0]);
            currentUser.setPassword(params[1]);
            currentUser.setFirstName(params[4]);
            currentUser.setLastName(params[5]);
            currentUser.setInitials(params[6]);
            currentUser.setPhoneNumber(params[7]);
            // set this variable temporarily and if the server returns that it has been set correctly,
            // assign it to the user object in postExecute()
            newPin = params[3];

            // Open connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
        }catch(IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "exception";
        }
        try{
            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if(response_code == HttpURLConnection.HTTP_OK){
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                return (result.toString().trim());
            }else{
                return("unsuccessful");
            }
        }catch(IOException e){
            e.printStackTrace();
            return "exception";
        }finally{
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result){
        // this method will be running on a UI thread

        if(result.equals("success")){
            Toast.makeText(settings_page, result, Toast.LENGTH_LONG).show();
            currentUser.setPinNumber(newPin);
            // give a brief wait so the user can see the login success Toast
            Intent intent = new Intent(settings_page, User_Logged_In.class);
            // this passes the serialized user object to the next activity for further use

            intent.putExtra("currentUser", currentUser);
            settings_page.startActivity(intent);
        }else if (result.equals("failure")) {
            // If username and password does not match display the error
            Toast.makeText(settings_page, "Login error!", Toast.LENGTH_LONG).show();
        }
        else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            Toast.makeText(settings_page, "Error. Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}