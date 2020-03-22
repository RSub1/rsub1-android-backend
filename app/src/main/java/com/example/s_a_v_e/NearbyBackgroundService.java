package com.example.s_a_v_e;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.s_a_v_e.bl.NearbyMessages;
import com.example.s_a_v_e.bl.SSEHandler;

public class NearbyBackgroundService extends Service
{
  private final static String TAG = "S-A-V-E";

  NearbyMessages messageHandler;

  public NearbyBackgroundService() {
    super();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    int res =  super.onStartCommand(intent, flags, startId);
    this.messageHandler = new NearbyMessages(this, new SSEHandler(this));
    SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    messageHandler.setup(sharedPref);

    Toast.makeText(this, messageHandler.getSave_id(), Toast.LENGTH_LONG).show();

    return res;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.messageHandler.destroy();

    Log.d(TAG, "onDestroy: Called");
  }


}
