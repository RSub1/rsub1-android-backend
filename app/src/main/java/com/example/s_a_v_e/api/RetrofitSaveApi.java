package com.example.s_a_v_e.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitSaveApi {
  @POST("v0/")
  Call<CmNewUserResponsePayload> postNew();
}

class CmInfectionInfo {
  boolean hadContactToInfectedPeople;
}

class CmPatchInfectionStatePayload {
  InfectionState state;
  String userId;
}

class CmPatchTogglesPayload {
  String[] enable;
  String[] disable;
}
enum InfectionState {
  CONFIRMED,
  PENDING ,
  HEALTHY
}
