package com.example.fooddilveryapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Constants {


    public static String userEmail;
    public static boolean IS_GUEST_USER;


    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context ctx, String msg) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getClipboardText(Context ctx) {
        String textToPaste = "";
        if (ctx == null) {
            return textToPaste;
        }
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(CLIPBOARD_SERVICE);
        try {
            if (clipboard != null) {
                textToPaste = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            } else {
                Constants.showToast(ctx, "Error. Please try again!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
           Constants.showToast(ctx, "Error. Please try again!");
        }

        return textToPaste;
    }

    public static void copyText(Context ctx, String label, String text) {
        if (ctx == null) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
           Constants.showToast(ctx, "Copied");
        } else {
            Constants.showToast(ctx, "Error. Please try again!");
        }
    }

    public static void hideSoftKeyboard(Context ctx, EditText edText) {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edText.getWindowToken(), 0);
    }

    public static boolean appInstalledOrNot(Context ctx, String uri) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static Calendar setAlarmTime() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR, 9);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        time.set(Calendar.AM_PM, Calendar.AM);
        if (System.currentTimeMillis() > time.getTimeInMillis()) {
            long add = AlarmManager.INTERVAL_DAY;
            long oldTime = time.getTimeInMillis();
            time.setTimeInMillis(oldTime + add);
        }
        return time;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void writeToSDFile(Context mContext, String jsonString) {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(mContext.getFilesDir() + "/download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "stickersData.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(jsonString);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("write+_file", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder readRaw(Context mContext) {
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(mContext.getFilesDir() + "/download/");
        String line = "";
        StringBuilder text = new StringBuilder();
        if (dir.exists()) {
            File file = new File(dir, "stickersData.txt");
            FileOutputStream os = null;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }
        }
        return text;
    }

    //check email format
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }




}