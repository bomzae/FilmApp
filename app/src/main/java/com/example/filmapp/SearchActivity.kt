package com.example.filmapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private val TAG: String = this::class.java.simpleName
    private val API_KEY: String = "de60360c8bbff1f2b861da8ce6a2c67a"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var retrofit: Retrofit
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recyclerview2)
        recyclerView.layoutManager = LinearLayoutManager(this)

        edtSearch = findViewById(R.id.edt_search)
        btnSearch = findViewById(R.id.btn_search)

        btnSearch.setOnClickListener {
            searchPeople(edtSearch.text.toString())
        }

        // Retrofit 초기화
        retrofit = Retrofit.Builder()
            .baseUrl("http://www.kobis.or.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchPeople(searchQuery: String) {
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.searchPeople(API_KEY, null, null, searchQuery, null)
            .enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    val peopleResult: Map<String, Any> = response.body()?.get("peopleListResult") as Map<String, Any>

                    // Adjust the method parameters according to the actual response structure
                    val peopleList: List<Map<String, Any>> = peopleResult["peopleList"] as List<Map<String, Any>>

                    val adapter = Adapter(peopleList)
                    recyclerView.adapter = adapter
                }

                override fun onFailure(call: Call<Map<String, Any>>, throwable: Throwable) {
                    // 실패 시 처리
                }
            })
    }
}