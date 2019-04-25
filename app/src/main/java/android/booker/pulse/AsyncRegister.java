package android.booker.pulse;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class AsyncRegister extends AsyncTask<String, String, String> {
    private Register_Page register_page;
    HttpURLConnection conn;
    URL url = null;
    User currentUser = new User();
    public AsyncRegister(Register_Page register_page){
        this.register_page = register_page;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // optional progress bar would go here
    }

    @Override
    protected String doInBackground(String... params){
        try{
            url = new URL("http://fea9dcfa.ngrok.io/pulse_api/register.php");
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
                    .appendQueryParameter("firstName", params[0])
                    .appendQueryParameter("lastName", params[1])
                    .appendQueryParameter("initials", params[2])
                    .appendQueryParameter("username", params[3])
                    .appendQueryParameter("phoneNumber", params[4])
                    .appendQueryParameter("password", params[5]);
            String query = builder.build().getEncodedQuery();

            // set the params to the User object for later verification
            currentUser.setUserName(params[0]);
            currentUser.setPassword(params[1]);
            currentUser.setFirstName(params[2]);
            currentUser.setLastName(params[3]);
            currentUser.setInitials(params[4]);
            currentUser.setPhoneNumber(params[5]);

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
                // Pass data to onPostExecute method
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

        if(result.contains("success")){
            Toast.makeText(register_page, "success", Toast.LENGTH_LONG).show();
            // give a brief wait so the user can see the login success Toast
            Intent intent = new Intent(register_page, Landing_Page.class);

            //intent.putExtra("currentUser", currentUser);
            register_page.startActivity(intent);
        }else if (result.equals("duplicate") ) {
            // show if primary key conflict
            Toast.makeText(register_page, result, Toast.LENGTH_LONG).show();
        }
        else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            Toast.makeText(register_page, "Error. Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}