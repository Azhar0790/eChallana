package in.techpinnacle.andriod.eChallan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.pojos.Citizen4Andriod;
import in.techpinnacle.andriod.eChallan.pojos.Offence4Anriod;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class CitizenActivity extends AppCompatActivity {

    String licence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);

        Intent i = getIntent();

        Citizen4Andriod citizen = (Citizen4Andriod)i.getSerializableExtra("citizen");
        licence=citizen.getLicense();
        ((TextView)findViewById(R.id.liLabel)).setText(citizen.getLicense());
        ((TextView)findViewById(R.id.nameLabel)).setText(citizen.getName());
        ((TextView)findViewById(R.id.adrrLabel)).setText(citizen.getAddress());
        ((TextView)findViewById(R.id.mailLabel)).setText(citizen.getMailId());
    }

    public void getHistory(View view){
        new GetHistory().execute(licence);
    }

    public void newOffence(View view){
        Intent i = new Intent(CitizenActivity.this,OffenceActivity.class);
        i.putExtra("licence",licence);
        startActivity(i);
    }

    class GetHistory extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Offence4Anriod[] offences;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (!TPUtils.isConnectingToInternet(CitizenActivity.this)) {
                    throw new Exception("Not Connected To Network! \n Can Not Reach Server");
                }

                ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
                args.add(new BasicNameValuePair("licence", String.valueOf(params[0])));
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                offences = jsonHttpClient.Get(ServiceUrl.GET_OFFENCES, args, Offence4Anriod[].class);
                if (offences == null)
                    throw new Exception("No Offences Recorded Till Date");

                args.clear();

            } catch (Exception e) {
                result = "Err: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

            progressDialog = TPUtils.getProgressDialog(CitizenActivity.this, "Please wait while we checck online...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (s == null) {
                                      Intent iNext=new Intent(CitizenActivity.this,HistoryActivity.class);
                                      iNext.putExtra("offences",offences);
                                      startActivity(iNext);
                                  } else if (s.startsWith("Err")) {
                                      TPUtils.showAlertDialog(CitizenActivity.this, "Not Found", s, R.mipmap.ic_launcher);
                                  }
                              }
                          }
            );
        }
    }
}
