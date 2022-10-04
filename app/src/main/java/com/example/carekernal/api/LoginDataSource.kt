package com.example.carekernal.api

import com.example.carekernal.BuildConfig
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginDataSource {
    companion object {
         const val Base_Url = "https://api.staging.carekernel.net/v1/"
    }

    inline fun <reified ApiService> buildApi(
        api: Class<ApiService>
    ): ApiService {
        return Retrofit.Builder().baseUrl(Base_Url).client(
            OkHttpClient.Builder().also { client ->
//                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    client.addInterceptor(logging)
                val dispatcher=Dispatcher()
                dispatcher.maxRequests=1
                client.dispatcher(dispatcher)
                client.retryOnConnectionFailure(true)
                client.connectTimeout(10,TimeUnit.SECONDS)
//                }
            }.build()
        )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(api)

    }

}