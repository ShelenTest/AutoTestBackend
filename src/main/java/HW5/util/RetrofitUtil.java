package HW5.util;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtil {

    private static String baseURL = "http://localhost:8189/market/api/v1/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static String getBaseURL() {
        return baseURL;
    }
}