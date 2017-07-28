package in.techpinnacle.andriod.eChallan.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.techpinnacle.andriod.eChallan.pojos.Police;
import in.techpinnacle.andriod.eChallan.pojos.WUser;

/**
 * Created by Vamsy on 18-Nov-15.
 */
public class TPUtils {

    public static Police currentUser;
    public static SimpleDateFormat sdfDefault = new SimpleDateFormat("dd-MMM-yyyy");

    public static String getCurrentDate() {
        final Calendar myCalendar = Calendar.getInstance();
        return sdfDefault.format(myCalendar.getTime());
    }

    public static void getDateFromUser(final TextView text) {

        final Calendar myCalendar = Calendar.getInstance();
        new DatePickerDialog(text.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                text.setText((new SimpleDateFormat("dd-MMM-yyyy")).format(myCalendar.getTime()));
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            Network[] networks;
            try {
                networks = connectivity.getAllNetworks();
                if (networks != null)
                    for (int i = 0; i < networks.length; i++) {
                        try {
                            if (connectivity.getNetworkInfo(networks[i]).getState() == NetworkInfo.State.CONNECTED) {
                                return true;
                            }
                        } catch (Exception e) {
                        }
                    }
            }catch(NoSuchMethodError ex){
                NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnectedOrConnecting();
            }

        }
        return false;
    }

    public static void showAlertDialog(final Context context, String title, String message, int icon) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setCancelable(false);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(icon);

        // Setting OK Button

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alertDialog.show();
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog progressDialog;

        SpannableString ss2 = new SpannableString(message);
        ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 0, ss2.length(), 0);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(ss2);

        return progressDialog;
    }

    public static String encrypt(String text) {
        String tempTxt = "";
        for (char ch : text.toCharArray())
            tempTxt += (char) (ch - 26);
        return tempTxt;
    }

    public static void showConfirmDialog(final Context context, String title, String message, int icon, final ConfirmDialogListener eventListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setCancelable(false);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(icon);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (eventListener != null)
                    eventListener.onDialogPositiveClick(alertDialog);
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (eventListener != null)
                    eventListener.onDialogNegativeClick(alertDialog);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public interface ConfirmDialogListener {
        public void onDialogPositiveClick(DialogInterface dialog);

        public void onDialogNegativeClick(DialogInterface dialog);
    }

    public static ArrayList<String> splitStringToLength(String str, int length) {
        ArrayList<String> result = new ArrayList<>();

        while (str.length() >= length) {
            result.add(str.substring(0, length));
            str = str.substring(length);
        }
        if (str.length() > 0) result.add(str);
        return result;
    }

    public static String arrangeStringToLength(String str, int length) {
        StringBuilder result = new StringBuilder();
        int curLength = 0;
        String[] words = str.split(" ");
        for (String word : words) {
            if ((curLength + word.length() + 1) > length) {
                result.append("\n");
                curLength = word.length() + 1;
            } else {
                curLength += word.length() + 1;
            }
            result.append(word + " ");
        }
        return result.toString();
    }


    public static InputStream openHttpConnection(String urlString) throws Exception
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection)) throw new IOException("Not an HTTP connection");


            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

        return in;
    }

    public static Bitmap downloadImage(String URL)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = openHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    public static Location getCurrentLocation(Context context){
        Location bestLocation = null;

        try {
            LocationManager mLocationManager = (LocationManager)context.getApplicationContext().getSystemService(context.LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }

        }catch(SecurityException e){
            e.printStackTrace();
            Toast.makeText(context, "No GPS Access", Toast.LENGTH_LONG).show();
        }

        return bestLocation;
    }

    public  static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}