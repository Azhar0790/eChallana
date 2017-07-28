package in.techpinnacle.andriod.eChallan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.pojos.WUser;
import in.techpinnacle.andriod.eChallan.utils.JSONHttpClient;
import in.techpinnacle.andriod.eChallan.utils.ServiceUrl;
import in.techpinnacle.andriod.eChallan.utils.TPUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PrefetchData().execute();


    }

    class PrefetchData extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(i);
            finish();
        }
    }
}
