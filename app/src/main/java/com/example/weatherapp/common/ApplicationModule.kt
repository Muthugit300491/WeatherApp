package com.example.weatherapp.common


import android.content.Context
import com.example.weatherapp.BuildConfig

import com.example.weatherapp.data.api.*


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

//    @Provides
//    @Singleton
//    fun provideApplicationContext(): Context = applic

 /*  @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)*/


    //val prefs: SharedPreferences = context.getSharedPreferences(prefsFILENAME, 0)
    /*@Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.createDataStore(
        name = "dott"
    )*/




    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        val requestInterceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder().build()

            var mAuth=""
            val request: Request = when (mAuth) {
                "" -> {
                    chain.request().newBuilder().url(url).build()
                }
                else -> {
                    chain.request().newBuilder().url(url)
                        .addHeader("Authorization", "Bearer " + "")
                        .build()
                }
            }
            return@Interceptor chain.proceed(request)
        }

        /*val mRequestInterceptor = RequestInterceptor(
            PreferencesHelper(provideApplicationContext(), provideSharedPreferences())
        )*/

        val mClientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(
                    AuthorizationInterceptor(
                    APIcallClient.getClient(
                        "EmpExit",
                        appContext
                    ),appContext)
                )
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)

        //.addInterceptor(AuthorizationInterceptor(appContext, provideApiService(retrofit)))

        //.addInterceptor(NoConnectionInterceptor(mActivity!!))


        mClientBuilder.build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,

    ): Retrofit {


        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            //.addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(APIcallClient.RSP_BASE_URL)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}