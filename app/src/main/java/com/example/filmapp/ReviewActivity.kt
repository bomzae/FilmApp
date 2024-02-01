package com.example.filmapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ReviewActivity : AppCompatActivity() {
    lateinit var reviewBtn: Button // 리뷰 저장 버튼
    var DB:DBHelper?=null

    private lateinit var edt_title: EditText
    private lateinit var edt_director: EditText
    private lateinit var spn_genre: Spinner
    private lateinit var edt_date: EditText
    private lateinit var rattingBar: RatingBar
    private lateinit var edt_summary: EditText

    private lateinit var editTextDate: EditText
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        edt_title = findViewById(R.id.edt_title)
        edt_director = findViewById(R.id.edt_director)
        spn_genre = findViewById(R.id.spn_genre)
        edt_date = findViewById(R.id.edt_date)
        rattingBar = findViewById(R.id.rattingBar)
        edt_summary = findViewById(R.id.edt_summary)

        editTextDate = findViewById(R.id.edt_date)
        ratingBar = findViewById(R.id.rattingBar)

        DB = DBHelper(this)

        reviewBtn = findViewById(R.id.btn_save)
        reviewBtn.setOnClickListener {// DB에 저장 후 메인 페이지로 이동
            val insert = DB!!.insertReview(edt_title.text.toString(), edt_director.text.toString(), spn_genre.selectedItem.toString(), edt_date.text.toString(), rattingBar.rating, edt_summary.text.toString())
            if (insert == true) {
                Toast.makeText(
                    this@ReviewActivity,
                    "저장되었습니다.",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this@ReviewActivity,
                    "오류가 발생했습니다.",
                    Toast.LENGTH_SHORT).show()
            }

            val loginIntent = Intent(this@ReviewActivity, HomeActivity::class.java)
            startActivity(loginIntent)
        }

        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // 날짜를 선택한 후의 동작을 여기에 구현
                    val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                    editTextDate.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }
}