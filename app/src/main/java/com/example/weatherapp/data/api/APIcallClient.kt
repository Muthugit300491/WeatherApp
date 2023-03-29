package com.example.weatherapp.data.api


import android.content.Context
import com.example.weatherapp.data.NoConnectionInterceptor


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit



object APIcallClient {
    const val RSP_BASE_URL = "https://api.openweathermap.org/"
    private lateinit var requestInterceptor: Interceptor
    private var mActivity: Context? = null
    private var apiService: ApiService? = null


    fun getClient(mAuth: String, activity: Context): ApiService {
        mActivity = activity
        requestInterceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder().build()

            //version
            val request: Request = when (mAuth) {
                "" -> {

                    chain.request().newBuilder().url(url).build()
                    //chain.request().newBuilder().url(url).build()
                }
                else->{
                    chain.request().newBuilder().url(url).build()
                }
             /*   else -> {
                    chain.request().newBuilder().url(url)
                        .addHeader("Authorization", "Bearer "+mPrefs.mToken)
                        .build()
                }*/
            }

            return@Interceptor chain.proceed(request)
        }
        return provideRetrofit()!!
    }

    private fun provideOkHttpClientAPI(): OkHttpClient? {

        /*val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag("OkHttp").d(message)
        })*/

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        //logging.level = HttpLoggingInterceptor.Level.BASIC

        val okhttpClientBuilder: OkHttpClient.Builder = if (mActivity != null) {
            OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(NoConnectionInterceptor(mActivity!!))
        } else {
            OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
        }

        //okhttpClientBuilder.addInterceptor(AddCookiesInterceptor(mActivity!!)); // VERY VERY IMPORTANT
        //okhttpClientBuilder.addInterceptor(ReceivedCookiesInterceptor(mActivity!!)); // VERY VERY IMPORTANT

        /*val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        okhttpClientBuilder.cookieJar(JavaNetCookieJar(cookieManager))*/




        return okhttpClientBuilder.build()
    }

    private fun provideOkHttpClient(): OkHttpClient? {

        val okhttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)

        return okhttpClientBuilder.build()
    }


    private fun provideRetrofit(): ApiService? {
        //if (apiService == null) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        apiService = Retrofit.Builder()
                .client(provideOkHttpClientAPI()!!)
                .baseUrl(RSP_BASE_URL)
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiService::class.java)
        //}
        return apiService
    }

    fun getApiService(): ApiService? {
        if (apiService == null) {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            apiService = Retrofit.Builder()
                    .client(provideOkHttpClient()!!)
                    .baseUrl(RSP_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(ApiService::class.java)
        }
        return apiService
    }
}