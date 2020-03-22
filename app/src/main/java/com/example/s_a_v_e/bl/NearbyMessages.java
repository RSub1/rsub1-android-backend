package com.example.s_a_v_e.bl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.s_a_v_e.api.CmNewUserResponsePayload;
import com.example.s_a_v_e.api.OkHttpSaveApi;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NearbyMessages {
  private static String TAG = "S-A-V-E";
  NearbyMessages _that;
  Context ctx;
  SSEHandler sseh;
  MessageListener mMessageListener;
  Message mMessage = new Message(Build.DEVICE.getBytes());

  private String save_id = "";

  OkHttpSaveApi mApi;
  InteractionManger iManager = new InteractionManger();

  public NearbyMessages(Context ctx, SSEHandler serverSentEventHandler){
    this.ctx = ctx;
    this._that = this;
    this.sseh = serverSentEventHandler;
    this.mApi = new OkHttpSaveApi();
  }

  public void setup(SharedPreferences sharedPref) {
    if(sharedPref.contains("SAVE_ID")){
      this.save_id = sharedPref.getString("SAVE_ID", "");
    }

    iManager.loadInteractions(sharedPref);
    subscribeToApi();

    if(save_id.equals("")) {
      getNewId(sharedPref);
    }
    else
      setupNearby();
  }

  private void subscribeToApi() {
    mApi.subscribeToServer(iManager.convertInteractions(), this.sseh);
  }

  private void getNewId(SharedPreferences sharedPref) {
    mApi.postNewId().enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        Log.e(TAG, "ERROR in request");
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        Gson gson = new Gson();
        CmNewUserResponsePayload resp = gson.fromJson(response.body().string(), CmNewUserResponsePayload.class);
        sharedPref.edit()
          .putString("SAVE_ID", resp.getId())
          .apply();

        _that.save_id = resp.getId();

        mMessage = new Message(_that.save_id.getBytes());
        setupNearby();
        Log.d(TAG, resp.getId());
      }
    });
  }

  public void setupNearby(){
    mMessageListener = initMessageListener();
    SubscribeOptions options = new SubscribeOptions.Builder()
      .setStrategy(Strategy.BLE_ONLY)
      .build();

    Nearby.getMessagesClient(ctx).publish(mMessage);
    Nearby.getMessagesClient(ctx).subscribe(mMessageListener, options);
  }

  public MessageListener initMessageListener(){
    return new MessageListener(){
      @Override
      public void onFound(Message message) {
        String msg = new String(message.getContent());
        Log.d(TAG, "Found message: " + msg);
        Toast.makeText(ctx, "Found: " +  msg, Toast.LENGTH_LONG).show();

        String id = msg; // TODO: extract id from msg

        iManager.startInteraction(id);
      }

      @Override
      public void onLost(Message message) {
        String msg = new String(message.getContent());
        Log.d(TAG, "Lost sight of message: " + msg);
        Toast.makeText(ctx, "Lost: " +  msg, Toast.LENGTH_LONG).show();

        String id = msg; // TODO: id must be extracted from msg

        iManager.endInteraction(id);
      }

      @Override
      public void onDistanceChanged(Message var1, Distance var2) {
        Log.d(TAG, "Distance changed: " + new String(var1.getContent()));
      }
    };
  }

  public String getSave_id() {
    return save_id;
  }

  public void setSave_id(String save_id) {
    this.save_id = save_id;
  }

  public void destroy() {
    Nearby.getMessagesClient(this.ctx).unpublish(mMessage);
    Nearby.getMessagesClient(this.ctx).unsubscribe(mMessageListener);
  }
}
