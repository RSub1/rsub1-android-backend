package com.example.s_a_v_e.bl;

import android.content.Context;
import android.util.EventLog;

import com.here.oksse.ServerSentEvent;

import okhttp3.Request;
import okhttp3.Response;

public class SSEHandler implements ServerSentEvent.Listener {
  Context ctx;
  public SSEHandler(Context ctx){
    this.ctx = ctx;
  }

  @Override
  public void onOpen(ServerSentEvent sse, Response response) {

  }

  @Override
  public void onMessage(ServerSentEvent sse, String id, String event, String message) {

  }

  @Override
  public void onComment(ServerSentEvent sse, String comment) {

  }

  @Override
  public boolean onRetryTime(ServerSentEvent sse, long milliseconds) {
    return false;
  }

  @Override
  public boolean onRetryError(ServerSentEvent sse, Throwable throwable, Response response) {
    return false;
  }

  @Override
  public void onClosed(ServerSentEvent sse) {

  }

  @Override
  public Request onPreRetry(ServerSentEvent sse, Request originalRequest) {
    return null;
  }
}
