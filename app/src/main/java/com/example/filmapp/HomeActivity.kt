package com.example.filmapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab


class HomeActivity : AppCompatActivity() {
    lateinit var rankFragment: Fragment
    lateinit var searchFragment: Fragment
    lateinit var myFragment: Fragment

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rankFragment = RankFragment()
        searchFragment = SearchFragment()
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
                    selected = searchFragment
                } else if (position == 2) {
                    selected = myFragment
                }
                supportFragmentManager.beginTransaction().replace(R.id.tab_layout_container, selected!!).commit()
            }

            override fun onTabUnselected(tab: Tab) { }
            override fun onTabReselected(tab: Tab) { }
        })
    }
}