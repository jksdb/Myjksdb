package com.example.asus.water.retrofit;


import android.telecom.Call;

import java.util.ArrayList;

import retrofit2.http.GET;

/**
 * Created by asus on 2017/6/7.
 */

public interface CityService {
    @GET("china-city-list.json")
      Call<ArrayList<CityBean>> getCity();
}
