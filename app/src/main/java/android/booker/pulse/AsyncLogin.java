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

public class AsyncLogin extends AsyncTask<String, String, String> {
    private Login_Page login_page;
    private HttpURLConnection conn;
    private URL url = null;
    private User currentUser = new User();
    public AsyncLogin(Login_Page login_page){
        this.login_page = login_page;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // optional progress bar would go here
    }

    @Override
    protected String doInBackground(String... params){
        try{
            url = new URL("http://fea9dcfa.ngrok.io/pulse_api/login.php");
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
                    .appendQueryParameter("password", params[1]);
            String query = builder.build().getEncodedQuery();

            // set the params to the User object
            currentUser.setUserName(params[0]);
            currentUser.setPassword(params[1]);

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
                try{
                    JSONArray jsonArray = new JSONArray(result.toString());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        currentUser.setUserName(object.getString("userID"));
                        currentUser.setPassword(object.getString("password"));
                        currentUser.setFirstName(object.getString("firstname"));
                        currentUser.setLastName(object.getString("lastname"));
                        currentUser.setPhoneNumber(object.getString("phoneNumber"));
                        currentUser.setInitials(object.getString("initials"));
                        currentUser.setPinNumber(object.getString("pinNumber"));
                    }
                }catch(JSONException e){
                    e.printStackTrace();
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

        if(result.contains(currentUser.getPassword())){
            Toast.makeText(login_page, result, Toast.LENGTH_LONG).show();
            // give a brief wait so the user can see the login success Toast
            Intent intent = new Intent(login_page, User_Logged_In.class);
            // this passes the serialized user object to the next activity for further use

            intent.putExtra("currentUser", currentUser);
            login_page.startActivity(intent);
        }else if (!result.contains(currentUser.getPassword())) {
            // If username and password does not match display the error
            Toast.makeText(login_page, "Login error!", Toast.LENGTH_LONG).show();
        }
        else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            Toast.makeText(login_page, "Error. Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}