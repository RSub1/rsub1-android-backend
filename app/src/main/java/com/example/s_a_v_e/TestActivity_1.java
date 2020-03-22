package com.example.s_a_v_e;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class TestActivity_1 extends AppCompatActivity {
  private final String TAG = "S-A-V-E TEST";
  MessageListener mMessageListener;
  Message mMessage = new Message("My ID".getBytes());
  TestActivity_1 _that;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this._that = this;
    this.mMessageListener = initMessageListener();
    Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
  }

  @Override
  public void onStart() {
    super.onStart();
    Nearby.getMessagesClient(this).publish(mMessage);
    Nearby.getMessagesClient(this).subscribe(mMessageListener);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void onStop() {
    super.onStop();
    Nearby.getMessagesClient(this).unpublish(mMessage);
    Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
  }

  public MessageListener initMessageListener(){
    return new MessageListener(){
      @Override
      public void onFound(Message message) {
        Log.d(TAG, "Found message: " + new String(message.getContent()));
        Toast.makeText(_that, "FOUND", Toast.LENGTH_LONG);
      }

      @Override
      public void onLost(Message message) {
        Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
        Toast.makeText(_that, "LOST", Toast.LENGTH_LONG);
      }

      @Override
      public void onDistanceChanged(Message var1, Distance var2) {
        Log.d(TAG, "Distance changed: " + new String(var1.getContent()));
        Toast.makeText(_that, "DISTANCE CHANGED", Toast.LENGTH_LONG);
      }
    };
  }
}
