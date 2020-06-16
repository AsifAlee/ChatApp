package com.example.chatapp.Fragments;

import com.example.chatapp.Notifications.MyResponse;
import com.example.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAFFiKLqg:APA91bHsjVJuDpf4YSdGAauziKp4qam45aFWJsUsSjnQJHSZvNAllFZGu1ZGOyjgGf7fgVQqTTnHxrFlVggUKoKvSR8MeM4kVEzVkX-oLcygQTYGHMcYl6DF-tqcYR2Y-3Vsn1P_lKwW"
            }

    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
