package com.example.filmapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFragment : Fragment() {
    var DB:DBHelper?=null

    lateinit var userName: TextView // 사용자 이름 텍스트
    lateinit var reviewBtn: Button // 리뷰 등록 버튼
    lateinit var collectionBtn: Button // 컬렉션 등록 버튼
    lateinit var reviewViewBtn: Button // 컬렉션 등록 버튼
    lateinit var collectionViewBtn: Button // 컬렉션 등록 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View

        view = inflater.inflate(R.layout.fragment_my, container, false)

        userName = view.findViewById(R.id.userName)
        reviewBtn = view.findViewById(R.id.reviewBtn)
        collectionBtn = view.findViewById(R.id.collectionBtn)
        reviewViewBtn = view.findViewById((R.id.reviewViewBtn))
        collectionViewBtn = view.findViewById(R.id.collectionViewBtn)

        reviewBtn.setOnClickListener {// 리뷰 등록 페이지로 이동
            val loginIntent = Intent(activity, ReviewActivity::class.java)
            startActivity(loginIntent)
        }

        collectionBtn.setOnClickListener {// 컬렉션 등록 페이지로 이동
            val loginIntent = Intent(activity, CollectionActivity::class.java)
            startActivity(loginIntent)
        }

        reviewViewBtn.setOnClickListener { // 리뷰 확인 페이지로 이동
            val loginIntent = Intent(activity, ReviewViewActivity::class.java)
            startActivity(loginIntent)
        }
        
        collectionViewBtn.setOnClickListener {// 컬렉션 확인 페이지로 이동
            val loginIntent = Intent(activity, CollectionViewActivity::class.java)
            startActivity(loginIntent)
        }

        // 사용자 이름 DB에서 받아서 표시하기
        DB = DBHelper(activity)
        var MyDB = DB!!.writableDatabase
        var cursor: Cursor
        cursor = MyDB!!.rawQuery("select * from recentLogin order by ROWID desc limit 1", null)
        cursor.moveToNext()
        userName.setText(cursor.getString(0))

        // Inflate the layout for this fragment
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}