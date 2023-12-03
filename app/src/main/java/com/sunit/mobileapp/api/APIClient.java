package com.sunit.mobileapp.api;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        APIClient.token = token;
    }

    //    private static String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE2OTY0NzYxOTksImlhdCI6MTY5NjM4OTgwNSwiYXV0aF90aW1lIjoxNjk2Mzg5Nzk5LCJqdGkiOiJkNzEzYTJiZC1jNDc1LTQyNGUtODk3NS0xNjliNjEyZjVhODciLCJpc3MiOiJodHRwczovL3Vpb3QuaXh4Yy5kZXYvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjRlM2E0NDk2LTJmMTktNDgxMy1iZjAwLTA5NDA3ZDFlZThjYiIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiZGNjYzIxZTEtNTFjZS00Yzk4LWFhMzktNDVhMjE4MjI2ZDQyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7Im9wZW5yZW1vdGUiOnsicm9sZXMiOlsicmVhZDptYXAiLCJyZWFkOnJ1bGVzIiwicmVhZDppbnNpZ2h0cyIsInJlYWQ6YXNzZXRzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJkY2NjMjFlMS01MWNlLTRjOTgtYWEzOS00NWEyMTgyMjZkNDIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJVc2VyIFRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIiwiZ2l2ZW5fbmFtZSI6IlVzZXIiLCJmYW1pbHlfbmFtZSI6IlRlc3QiLCJlbWFpbCI6InVzZXJAaXh4Yy5kZXYifQ.DEEw2v7YF-XXIgJ96Bca1jHmOutJTlil8Ccf7x3BAQYzq129ZbWvWA2WQ6QW8k-u4qni65-b40NN5ygJDxkqb3HsKZn6yvE1ilLOXng-HS0rBfBKyHWH8DJyJ7eXMC2BIAKRKz1ncdkKZI36xOzrwFUWZmT_mbDdvBojiTr4JR5HG0A3CD2SFgrx01vELy7sBrDhlRDYWAGhWygSeW3ZxIViygDl3-VIWTqYGpP7mjtdF60Kr1fzNImR4qvp-t6oVCtoso4B7Feie4sp9X73d1yV-q8qZydwdAmKI8Iiqp2IH6TwbMLn9Jx8Hdh7puNgN1r4d72gz5F-Nta9qvopSg";
    private static String token= getToken();//"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDE2NTYwMjAsImlhdCI6MTcwMTU2OTYyMCwianRpIjoiZTVkOTBmMDEtZjlkZC00NDFiLWE3MzgtZDFiNWNhMWRjZjc3IiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0ZTNhNDQ5Ni0yZjE5LTQ4MTMtYmYwMC0wOTQwN2QxZWU4Y2IiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvcGVucmVtb3RlIiwic2Vzc2lvbl9zdGF0ZSI6IjQ3YzM5N2Y5LWVlYmItNGU5Mi05Njc1LWUzYjc3NmFkNjEyNyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91aW90Lml4eGMuZGV2Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJvcGVucmVtb3RlIjp7InJvbGVzIjpbInJlYWQ6bWFwIiwicmVhZDpydWxlcyIsInJlYWQ6aW5zaWdodHMiLCJyZWFkOmFzc2V0cyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiNDdjMzk3ZjktZWViYi00ZTkyLTk2NzUtZTNiNzc2YWQ2MTI3IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiRmlyc3QgTmFtZSBMYXN0IG5hbWUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIiwiZ2l2ZW5fbmFtZSI6IkZpcnN0IE5hbWUiLCJmYW1pbHlfbmFtZSI6Ikxhc3QgbmFtZSIsImVtYWlsIjoidXNlckBpeHhjLmRldiJ9.NjKkNo0USLEqiLVUfNIhTkqW_MY-zl73VoSATJmeKkDEf5EB2Wq5giMclHq9c1tb66abznOmq2NW7xXqGY9hr5UAVlXt2IxeuvNyPwV1ipAEgUl0loHvckTEpXeYZhIp4N_jdvmIYnKcaEuIZxIKeolepPZBFGOx-YaZwsKxI99K9G3MlUczrE0uJB_Aems_todbfw-QdtTDkAyr3ZI_AeBXg-fzVUtFwTrqDmM-61Cp77Hq3ZmDpWuEJs0qY9rpPsEXMlP822fnjrSZQchdgWYBFlip0SoSnYD-DuOMzkKBAkJ9fjq0squJ_3ticDjN-cgDLnkLS9U4iRDkTCW1HA";
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            //Bear token
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            });

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Retrofit getClient() {
        OkHttpClient client = getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}

