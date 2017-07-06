package com.arusoft.roomlibraryexample.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.arusoft.roomlibraryexample.data.database.util.DataBaseConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

import javax.inject.Inject;

/**
 * @FileName DeviceUtils.java
 * @Purpose
 * @Author Allstate Insurance
 * @RevisionHistory Created
 */
public class DeviceUtils {

    private static final String TAG = DeviceUtils.class.getName();
    private Context mContext;

    @Inject
    public DeviceUtils(Context context) {
        this.mContext = context;
    }

    /**
     * Detects the availability of a working network connection to open a
     * http/https connection.
     *
     * @return true if network is available, false otherwise.
     */

    public boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr != null) {
            NetworkInfo[] info = conMgr.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo != null
                            && anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifies if GPS is enabled.
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public boolean isGpsEnabled() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            String providers = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (TextUtils.isEmpty(providers)) {
                return false;
            }
            return providers.contains(LocationManager.GPS_PROVIDER);
        } else {
            final int locationMode;
            try {
                locationMode = Settings.Secure.getInt(
                        mContext.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            switch (locationMode) {

                case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
                case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
                    return true;
                case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
                case Settings.Secure.LOCATION_MODE_OFF:
                default:
                    return false;
            }
        }
    }


    /**
     * Exports Data Base
     *
     * @throws IOException
     */
    public void exportDataBases() throws IOException {
        //FIXME: This must work only on Debug mode
        writeToSD(DataBaseConstants.DATA_BASE_NAME);
    }

    /**
     * Exports the data base file to /sdcard folder with the name mac_technique_test.db
     * This method only works on Debug Mode
     *
     * @throws IOException
     */
    private void writeToSD(String databaseName) throws IOException {
        String DB_PATH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DB_PATH = mContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        } else {
            DB_PATH = mContext.getFilesDir().getPath() + mContext.getPackageName() + "/databases/";
        }
        File sd = Environment.getExternalStorageDirectory();

        if (sd.canWrite()) {
            String currentDBPath = databaseName;
            String backupDBPath = "demo_" + databaseName;
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d(TAG, "exported-path:" + backupDB.getPath());
            }
        }
    }

    /**
     * Exports a String into a file
     *
     * @param log
     */
    public void exportLog(String log) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File logfile = new File(externalStorageDirectory, "example_room_library.txt");

        if (externalStorageDirectory.canWrite()) {

            try {

                logfile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(logfile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                myOutWriter.append(log + "\n");
                myOutWriter.close();

                fOut.flush();
                fOut.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
