package com.example.filmapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass.
 * Use the [RankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankFragment : Fragment(R.layout.fragment_rank) {
    private val TAG: String = this::class.java.simpleName
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private val baseUrl: String = "http://www.kobis.or.kr"
    private val API_KEY: String = "de60360c8bbff1f2b861da8ce6a2c67a"
    private lateinit var retrofit: Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_recyclerview)

        val now: LocalDate = LocalDate.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedNow: String = (now.format(formatter).toInt() - 1).toString()

        Log.d("current-Date :", formattedNow)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getBoxOffice(API_KEY, formattedNow).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                val boxOfficeResult: Map<String, Any> = response.body()?.get("boxOfficeResult") as Map<String, Any>
                val jsonList: ArrayList<Map<String, Any>> = boxOfficeResult["dailyBoxOfficeList"] as ArrayList<Map<String, Any>>
                mAdapter = MyAdapter(jsonList)

                recyclerView.adapter = mAdapter
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
         * @return A new instance of fragment RankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RankFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}