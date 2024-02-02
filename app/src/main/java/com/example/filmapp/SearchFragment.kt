package com.example.filmapp

import android.os.Bundle
import android.view.View
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private val TAG: String = this::class.java.simpleName
    private val API_KEY: String = "de60360c8bbff1f2b861da8ce6a2c67a"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var retrofit: Retrofit
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerview2)
        recyclerView.layoutManager = LinearLayoutManager(activity)

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
            }
    }
}