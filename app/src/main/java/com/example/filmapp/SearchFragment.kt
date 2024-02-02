package com.example.filmapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {
    private val TAG: String = this::class.java.simpleName
    private val API_KEY: String = "de60360c8bbff1f2b861da8ce6a2c67a"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var retrofit: Retrofit
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerview2)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        edtSearch = view.findViewById(R.id.edt_search)
        btnSearch = view.findViewById(R.id.btn_search)

        btnSearch.setOnClickListener {
            searchPeople(edtSearch.text.toString())
        }

        // Retrofit 초기화
        retrofit = Retrofit.Builder()
            .baseUrl("http://www.kobis.or.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return view
    }

    private fun searchPeople(searchQuery: String) {
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.searchPeople(API_KEY, null, null, searchQuery, null)
            .enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    val peopleResult: Map<String, Any> = response.body()?.get("peopleListResult") as Map<String, Any>

                    // Adjust the method parameters according to the actual response structure
                    val peopleList: List<Map<String, Any>> = peopleResult["peopleList"] as List<Map<String, Any>>

                    adapter = Adapter(peopleList)
                    recyclerView.adapter = adapter
                }

                override fun onFailure(call: Call<Map<String, Any>>, throwable: Throwable) {
                    // 실패 시 처리
                }
            })
    }
}