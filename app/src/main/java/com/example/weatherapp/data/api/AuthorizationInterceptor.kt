package com.example.weatherapp.data.api

import android.content.Context



import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import java.io.IOException

class AuthorizationInterceptor(
    private val apiService: ApiService, private val mActivity: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var mainResponse = chain.proceed(chain.request())
        val mainRequest: Request = chain.request()
        // if response code is 401 or 403, 'mainRequest' has encountered authentication error
        val m = mainResponse.body

        when (mainResponse.code) {

            200 -> {
                //Show NotFound Message
                localToast("200", mainResponse.message)
            }

            201 -> {
                //Show NotFound Message
                localToast("201", mainResponse.message)
            }

            400 -> {
                //Show Bad Request Error Message
                localToast("400", mainResponse.message)
            }
            401 -> {
                //Show UnauthorizedError Message
                localToast("401", mainResponse.message)
                /*val mPrefs = Prefs(mActivity)
                val mRefreshToken = RefreshToken(mPrefs.mRefreshToken, mPrefs.mToken)
                val callSync: Call<MainObj<CheckLoginRes>> =
                    apiService.getRefreshTokenAsyn(mRefreshToken)
                try {
                    val response = callSync.execute()
                    val it = response.body()
                    val mIt = response.raw().body
                    if (it!!.status) {
                        // retry the 'mainRequest' which encountered an authentication error
                        // add new token into 'mainRequest' header and request again

                        it.response?.let { mCheckLoginRes ->

                            mPrefs.mToken = mCheckLoginRes.token
                            //AppController.mToken = mCheckLoginRes.refreshToken
                            val builder =
                                mainRequest.newBuilder()
                                    //.header("Authorization", AppController.mToken)
                                    .method(mainRequest.method, mainRequest.body)
                            mainResponse = chain.proceed(builder.build())

                        }
                        return mainResponse
                    } else {
                        mIt!!.close()
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }*/
            }
            403 -> {
                //Show Forbidden Message
                localToast("403", mainResponse.message)
            }

            404 -> {
                //Show NotFound Message
                localToast("404", mainResponse.message)
            }

            500 -> {
                //Show NotFound Message
                localToast("500", mainResponse.message)
            }

        }

        return mainResponse
    }

    private fun localToast(statusCode: String, message: String) {
        /*mActivity.runOnUiThread {
            mActivity.showToast("Status code id $statusCode $message")
        }*/
    }
}