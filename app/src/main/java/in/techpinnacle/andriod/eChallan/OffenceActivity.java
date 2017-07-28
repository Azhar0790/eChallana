package in.techpinnacle.andriod.eChallan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.pojos.Offence4Anriod;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class OffenceActivity extends AppCompatActivity {

    String licence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offence);
        Intent i = getIntent();
        licence=i.getStringExtra("licence");
        ((TextView)findViewById(R.id.li)).setText(licence);

        Spinner spinner = (Spinner) findViewById(R.id.vType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vtypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.payMode);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.pmodes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }

    public void recordOffence(View view){

        String fine = ((EditText)findViewById(R.id.fine)).getText().toString();
        String desc = ((EditText)findViewById(R.id.offenceDesc)).getText().toString();
        String vid=((EditText)findViewById(R.id.vid)).getText().toString();
        String vtype=(String)((Spinner)findViewById(R.id.vType)).getSelectedItem();
        boolean isPaid=((Spinner)findViewById(R.id.payMode)).getSelectedItem().equals("Cash");
        new AddOffence().execute(licence,fine,desc,vid,vtype,String.valueOf(isPaid));
    }

    class AddOffence extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Offence4Anriod offence;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (!TPUtils.isConnectingToInternet(OffenceActivity.this)) {
                    throw new Exception("Not Connected To Network! \n Can Not Reach Server");
                }

                ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
                args.add(new BasicNameValuePair("licence", params[0]));
                args.add(new BasicNameValuePair("fine", params[1]));
                args.add(new BasicNameValuePair("desc", params[2]));
                args.add(new BasicNameValuePair("vid", params[3]));
                args.add(new BasicNameValuePair("vtype", params[4]));
                args.add(new BasicNameValuePair("isPaid", params[5]));
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                offence = jsonHttpClient.Get(ServiceUrl.ADD_OFFENCE, args, Offence4Anriod.class);
                if (offence == null)
                    throw new Exception("Service Failure! Regret Inconvinience");

                args.clear();

            } catch (Exception e) {
                result = "Err: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

            progressDialog = TPUtils.getProgressDialog(OffenceActivity.this, "Please wait while we checck online...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (s == null) {
                                      Toast.makeText(OffenceActivity.this,"Offence Recorded Successfully",Toast.LENGTH_LONG).show();
                                      finish();
                                  } else if (s.startsWith("Err")) {
                                      TPUtils.showAlertDialog(OffenceActivity.this, "Not Found", s, R.mipmap.ic_launcher);
                                  }
                              }
                          }
            );
        }
    }
}
