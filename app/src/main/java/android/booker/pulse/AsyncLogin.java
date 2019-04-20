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
    HttpURLConnection conn;
    URL url = null;
    User currentUser = new User();
    public AsyncLogin(Login_Page login_page){
        this.login_page = login_page;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // optional progress bar or something or whatever needs to happen before doInBackground
    }

    @Override
    // doInBackground accepts string parameters from the AsyncLogin.execute() method in Login_Page
    protected String doInBackground(String... params){
        try{
            url = new URL("http://6698522f.ngrok.io/pulse_api/login.php");
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

            // These instance variables are set to true to signify that we are both sending and
            // receiving data.
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Uri.Builder helps to create a Uniform Resource Identifier
            // this string of characters will identify the specific resource to be
            // accessed from the server
            Uri.Builder builder = new Uri.Builder()
            // The parameters that are passed into the AsyncLogin object's execute() method which is
            // called as the object is instantiated on line 59 of Login_Page
                    .appendQueryParameter("username", params[0])
                    .appendQueryParameter("password", params[1]);
            //the completed URI is formed and placed in the query variable
            String query = builder.build().getEncodedQuery();

            // The same arguments that were passed into AsyncLogin.execute() from Login_Page
            // are assigned to an instance of the User object(created on line 27).
            // The object from this point forward will be passed from activity to activity to
            // maintain its state.
            currentUser.setUserName(params[0]);
            currentUser.setPassword(params[1]);

            // Get the output stream and associate it with the url variable declared above
            OutputStream outputStream = conn.getOutputStream();
            // creates a BufferedWriter object and passes an OutputStreamWriter object as the
            // argument. We are using the default buffer size.
            BufferedWriter writer = new BufferedWriter(
            // The OutputStreamWriter object takes the outputStream variable and and encodes it
            // into bytes using UTF-8 character set
                    new OutputStreamWriter(outputStream, "UTF-8"));
            // writer.write() writes text to the output stream.
            writer.write(query);
            // writer.flush() flushes the characters from the output stream buffer
            // to the stream that is sent to the server
            writer.flush();
            // writer.close() flushes the output stream
            writer.close();
            // The output stream is closed and all resources associated with it are cleared.
            outputStream.close();

            conn.connect();
        }catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                        currentUser.setUserName(object.getString("username"));
                        currentUser.setPassword(object.getString("password"));
                        currentUser.setFirstName(object.getString("firstname"));
                        currentUser.setLastName(object.getString("lastname"));
                        currentUser.setInitials(object.getString("initials"));
                        currentUser.setInitials(object.getString("phoneNumber"));
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