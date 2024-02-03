package com.example.filmapp

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ReviewViewActivity : AppCompatActivity() {
    var DB:DBHelper?=null

    lateinit var backBtn: Button
    lateinit var root_layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_view)

        root_layout = findViewById(R.id.root_layout)

        DB = DBHelper(this)
        var MyDB = DB!!.writableDatabase
        var cursor: Cursor
        cursor = MyDB!!.rawQuery("select * from recentLogin order by ROWID desc limit 1", null)
        cursor.moveToNext()
        var userName = cursor.getString(0)

        cursor = MyDB!!.rawQuery("select * from review where username = ?", arrayOf(userName))
        var recordCount = cursor.getCount() // 레코드 개수

        for(i:Int in 1..recordCount){

            val layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // CardView width
                ViewGroup.LayoutParams.WRAP_CONTENT // CardView height
            )

            layoutParams.setMargins(16,16,16,50)

            // 새 카드뷰 생성
            val reviewCardView = CardView(this)
            
            // 카드뷰 레이아웃 적용
            reviewCardView.layoutParams = layoutParams             
            
            // 카드뷰 코너 둥글게
            reviewCardView.radius = 12F             
            
            // 패딩 설정
            reviewCardView.setContentPadding(25,25,25,25)

            // 백그라운드 컬러 설정
            //reviewCardView.setCardBackgroundColor(#DFE4F3)

            reviewCardView.cardElevation = 8F
            reviewCardView.maxCardElevation = 12F

            // 리니어 레이아웃에 카드뷰 추가
            cursor.moveToNext()
            reviewCardView.addView(generateCardView(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getFloat(5), cursor.getString(6)))

            // 루트 레이아웃에 추가
            root_layout.addView(reviewCardView)
        }

        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val loginIntent = Intent(this@ReviewViewActivity, HomeActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun generateCardView(filmTitle: String?, filmDirector: String?, filmGenre: String?, filmDate: String?, filmRating: Float, filmSummary: String?): LinearLayout {
        val cardLinearLayout = LinearLayout(this)
        cardLinearLayout.orientation = LinearLayout.VERTICAL

        val title = TextView(this)
        title.text = filmTitle
        title.textSize = 24f
        title.setTextColor(Color.BLACK)

        val director = TextView(this)
        director.text = filmDirector
        director.textSize = 17f
        director.setTextColor(Color.BLACK)

        val genre = TextView(this)
        genre.text = filmGenre
        genre.textSize = 17f
        genre.setTextColor(Color.BLACK)

        val date = TextView(this)
        date.text = filmDate
        date.textSize = 17f
        date.setTextColor(Color.BLACK)

        val ratingLinearLayout = LinearLayout(this)
        ratingLinearLayout.orientation = LinearLayout.HORIZONTAL

        val rating = RatingBar(this)
        rating.rating = filmRating
        rating.numStars = 5
        rating.maxWidth = 0
        rating.setIsIndicator(true)

        val summary = TextView(this)
        summary.text = filmSummary
        summary.textSize = 17f
        summary.setTextColor(Color.BLACK)

        cardLinearLayout.addView(title)
        cardLinearLayout.addView(director)
        cardLinearLayout.addView(genre)
        cardLinearLayout.addView(date)
        ratingLinearLayout.addView(rating)
        cardLinearLayout.addView(ratingLinearLayout)
        cardLinearLayout.addView(summary)
        return cardLinearLayout
    }

}