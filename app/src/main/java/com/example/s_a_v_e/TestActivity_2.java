package com.example.s_a_v_e;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s_a_v_e.bl.NearbyMessages;
import com.example.s_a_v_e.bl.SSEHandler;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class TestActivity_2 extends AppCompatActivity {
  private final String TAG = "S-A-V-E TEST 2";

  NearbyMessages messageHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    messageHandler = new NearbyMessages(this, new SSEHandler(this));
  }

  @Override
  public void onStart() {
    super.onStart();
    SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    messageHandler.setup(sharedPref);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void onStop() {
    super.onStop();
    messageHandler.destroy();
  }
}
