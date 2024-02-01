package com.example.filmapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    // @GET은 Http Method로, [ GET / POST / PUT / DELETE / HEAD ] 중 무슨 작업인지 표현
    // ()안에는 URI에서 URL을 제외한 End Point(URI)
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    fun getBoxOffice(
        // 메소드명
        // @Query에서 String key에 해당하는 부분이 key에 값을 대입.
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): Call<Map<String, Any>>
}