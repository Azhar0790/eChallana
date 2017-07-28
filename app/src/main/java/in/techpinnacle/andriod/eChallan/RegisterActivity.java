package in.techpinnacle.andriod.eChallan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.techpinnacle.andriod.eChallan.pojos.Police;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void doSignUp(View view){
        EditText uid = (EditText) findViewById(R.id.userId);
        TextView tv1 = (TextView) findViewById(R.id.tvdob);
        TextView tv2 = (TextView) findViewById(R.id.tvdoj);
        new RegProcess().execute(uid.getText().toString(),tv1.getText().toString(),tv2.getText().toString());
    }

    public void doSelectDate(View view){
        TextView tv = (TextView)view;
        TPUtils.getDateFromUser(tv);
    }

    class RegProcess extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Police user;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (!TPUtils.isConnectingToInternet(RegisterActivity.this)) {
                    throw new Exception("Not Connected To Network! \n Can Not Reach Server");
                }

                ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
                args.add(new BasicNameValuePair("wid", String.valueOf(params[0])));
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                user = jsonHttpClient.Get(ServiceUrl.GET_USER, args, Police.class);
                if (user != null) {
                     if(user.getDob().equals(params[1]) && user.getDoj().equals(params[2])){
                        user = jsonHttpClient.Get(ServiceUrl.SET_USER, args, Police.class);
                        if (user != null) {
                            result=null;
                        }else
                            throw new Exception("Something Went Wrong On Server");
                    }else
                        throw new Exception("Incorrecct DOB and/or DOJ as per our records on server ");
                         //throw new Exception(user.getDob() +":"+user.getDoj());
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

            progressDialog = TPUtils.getProgressDialog(RegisterActivity.this, "Please wait while we authenticate online...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (s == null) {
                                      TPUtils.showAlertDialog(RegisterActivity.this, "Information", "REgistration Done", R.mipmap.ic_launcher);
                                      Intent iNext=new Intent(RegisterActivity.this,LogInActivity.class);
                                      startActivity(iNext);
                                      finish();
                                  } else if (s.startsWith("Err")) {
                                      TPUtils.showAlertDialog(RegisterActivity.this, "Access Denied", s, R.mipmap.ic_launcher);
                                  }
                              }
                          }
            );
        }
    }
}
