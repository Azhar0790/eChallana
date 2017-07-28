package in.techpinnacle.andriod.eChallan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.pojos.Police;
import in.techpinnacle.andriod.eChallan.pojos.WUser;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void doSignIn(View view){
        EditText uid = (EditText) findViewById(R.id.userId);
        //TPUtils.showAlertDialog(LoginActivity.this, "Connection Denied", "Unable To Reach The Server", R.mipmap.ic_launcher);
        new LogInProcess().execute(uid.getText().toString());
    }
    public void doSignUp(View view){
        Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    class LogInProcess extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Police user;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (!TPUtils.isConnectingToInternet(LogInActivity.this)) {
                    throw new Exception("Not Connected To Network! \n Can Not Reach Server");
                }

                ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
                args.add(new BasicNameValuePair("wid", String.valueOf(params[0])));
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                // SQLiteDatabase db = new GccTabDBHelper(LoginActivity.this).getWritableDatabase();
                user = jsonHttpClient.Get(ServiceUrl.GET_USER, args, Police.class);
                if (user != null) {
                    if(user.getPwd().equals("")||user.getPwd().equals(user.getwId())) {
                        throw new Exception("User Not Registered! Please Sign Up Before Signing In!");
                    }
                    result=null;
                }else
                    throw new Exception("No Such User Id Found On Server");

                args.clear();

            } catch (Exception e) {
                result = "Err: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

            progressDialog = TPUtils.getProgressDialog(LogInActivity.this, "Please wait while we authenticate online...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (s == null) {
                                      Intent iNext=null;
                                      TPUtils.currentUser=user;
                                      iNext = new Intent(LogInActivity.this, PoliceActivity.class);
                                      startActivity(iNext);
                                      finish();

                                  } else if (s.startsWith("Err")) {
                                      TPUtils.showAlertDialog(LogInActivity.this, "Access Denied", s, R.mipmap.ic_launcher);
                                  }
                              }
                          }
            );
        }
    }
}

