package com.example.oldguard_guardianver.intent

import android.app.Activity.RESULT_OK
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK
import com.example.oldguard_guardianver.Activity.ElderlyManagerActivity
import com.example.oldguard_guardianver.App
import com.example.oldguard_guardianver.HowIService
import com.example.oldguard_guardianver.Request.GuestNameResponse
import com.example.oldguard_guardianver.databinding.ActivityCodeIssueBinding
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**  '설정이 모두 끝났어요' 안내 화면  */
class MainIntent : AppCompatActivity() {
    private lateinit var viewBinding: ActivityCodeIssueBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        viewBinding = ActivityCodeIssueBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        super.onCreate(savedInstanceState)
        var request = GuestNameResponse("name")
        var gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${App.token_prefs.accessToken}")
                .build()
            chain.proceed(newRequest)
        }.build()

        var retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        var server = retrofit.create(HowIService::class.java)
        var temp = ""
        server.getResponse(request).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("성공", response.body().toString().substring(9,12))
                temp = response.body().toString().substring(9,12)
            }
        })

        viewBinding.nextBtn.setOnClickListener {
            var intent = Intent(this, ElderlyManagerActivity::class.java)
            intent.putExtra("name", temp);
            //설정종료 버튼 누르면 ElderlyMangerActivity ~ CodeIssueActivity까지 모두 종료 후 새로운 ElderMangerActivity 생성
            //flag는??
            startActivity(intent)

        }
    }
}