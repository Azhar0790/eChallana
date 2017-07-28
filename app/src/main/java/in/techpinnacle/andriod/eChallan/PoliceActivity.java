package in.techpinnacle.andriod.eChallan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.pojos.Citizen4Andriod;
import in.techpinnacle.andriod.eChallan.pojos.Police;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class PoliceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
    }

    public void doFetechCitizen(View view){
        EditText edt = (EditText)findViewById(R.id.licence);
        new NextProcess().execute(edt.getText().toString());
    }

    class NextProcess extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Citizen4Andriod citizen;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (!TPUtils.isConnectingToInternet(PoliceActivity.this)) {
                    throw new Exception("Not Connected To Network! \n Can Not Reach Server");
                }

                ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
                args.add(new BasicNameValuePair("licence", String.valueOf(params[0])));
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                citizen = jsonHttpClient.Get(ServiceUrl.GET_CITIZEN, args, Citizen4Andriod.class);
                if (citizen == null)
                    throw new Exception("No Such Record Found On Server");

                args.clear();

            } catch (Exception e) {
                result = "Err: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

            progressDialog = TPUtils.getProgressDialog(PoliceActivity.this, "Please wait while we checck online...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (s == null) {
                                      Intent iNext=new Intent(PoliceActivity.this,CitizenActivity.class);
                                      iNext.putExtra("citizen",citizen);
                                      startActivity(iNext);

                                  } else if (s.startsWith("Err")) {
                                      TPUtils.showAlertDialog(PoliceActivity.this, "Not Found", s, R.mipmap.ic_launcher);
                                  }
                              }
                          }
            );
        }
    }
}
