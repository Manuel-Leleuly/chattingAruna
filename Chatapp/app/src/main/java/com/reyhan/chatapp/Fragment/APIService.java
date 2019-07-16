package com.reyhan.chatapp.Fragment;

import com.reyhan.chatapp.Notification.MyResponse;
import com.reyhan.chatapp.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA8Bh9YJU:APA91bG0E-RkyK3smf7JLR1B6xwZDdtUkmM8Xe4MMeJQJrvb2LMFkZYCT0quP_yKeVS7IUDbJFTPIuC6qJsk9jR9Ym9gCOSdkHhoYvxq_6w9kkHof9lsxybYWnb_P3qxCf7qMy8TCeXC"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
