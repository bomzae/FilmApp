package com.example.filmapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab


class HomeActivity : AppCompatActivity() {
    lateinit var sqlDB: SQLiteDatabase
    lateinit var resetBtn: Button // DB 초기화 버튼
    var DB: DBHelper?=null
    lateinit var rankFragment: Fragment
    lateinit var locationFragment: Fragment
    lateinit var myFragment: Fragment

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rankFragment = RankFragment()
        locationFragment = LocationFragment()
        myFragment = MyFragment()

        supportFragmentManager.beginTransaction().add(R.id.tab_layout_container, rankFragment).commit()

        val tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                val position = tab.position
                var selected: Fragment?=null

                if (position == 0) {
                    selected = rankFragment
                } else if (position == 1) {
                    selected = locationFragment
                    val intent = Intent(applicationContext, MapsActivity::class.java)
                    startActivity(intent)
                } else if (position == 2) {
                    selected = myFragment
                }
                supportFragmentManager.beginTransaction().replace(R.id.tab_layout_container, selected!!).commit()
            }

            override fun onTabUnselected(tab: Tab) { }
            override fun onTabReselected(tab: Tab) { }
        })

        DB = DBHelper(this)

        resetBtn = findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener {
            sqlDB = DB!!.writableDatabase
            DB!!.onUpgrade(sqlDB, 1, 2)
            sqlDB.close()
        }
    }
}