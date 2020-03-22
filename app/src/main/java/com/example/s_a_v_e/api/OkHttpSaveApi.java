package com.example.s_a_v_e.api;

import com.google.gson.Gson;
import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpSaveApi {
  private OkHttpClient client = new OkHttpClient();
  private String baseURL = "https://rsub1.testoknof.de/";

  public Call postNewId() {
    RequestBody body = RequestBody.create(null, "");
    Request req = new Request.Builder()
      .url(baseURL + "v0/")
      .post(body)
      .build();

    return client.newCall(req);
  }

  public ServerSentEvent subscribeToServer(CmNotificationSubscriptionOptions cms, ServerSentEvent.Listener listener){
    Gson gson = new Gson();
    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(cms));
    Request req = new Request.Builder()
      .url(baseURL + "v0/_self/notifications")
      .post(body)
      .get()
      .build();

    OkSse okSse = new OkSse();
    return okSse.newServerSentEvent(req, listener);
  }
}
