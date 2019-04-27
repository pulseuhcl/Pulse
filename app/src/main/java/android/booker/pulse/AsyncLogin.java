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

public class AsyncLogin extends AsyncTask<User, String, String> {
    private Login_Page login_page;
    private HttpURLConnection conn;
    private URL url = null;

    public AsyncLogin(Login_Page login_page){
        this.login_page = login_page;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // optional progress bar would go here
    }

    @Override
    protected String doInBackground(User... users){
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
                    .appendQueryParameter("username", users[0].getUserName())
                    .appendQueryParameter("password", users[0].getPassword());
            String query = builder.build().getEncodedQuery();

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
            }
            else{
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
        User retrievedUser = new User();

        Intent intent = new Intent(login_page, User_Logged_In.class);
        // this passes the serialized user object to the next activity for further use
        if(!result.equals("[]")){
            try{
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    retrievedUser.setUserName(object.getString("userID"));
                    retrievedUser.setPassword(object.getString("password"));
                    retrievedUser.setFirstName(object.getString("firstname"));
                    retrievedUser.setLastName(object.getString("lastname"));
                    retrievedUser.setPhoneNumber(object.getString("phoneNumber"));
                    retrievedUser.setInitials(object.getString("initials"));
                    retrievedUser.setPinNumber(object.getString("pinNumber"));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            intent.putExtra("currentUser", retrievedUser);
            login_page.startActivity(intent);
        }
        else{
            Toast.makeText(login_page, "Incorrect login information", Toast.LENGTH_LONG).show();
        }
    }
}