package com.example.filmapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFragment : Fragment() {
    lateinit var reviewBtn: Button // 리뷰 등록 버튼
    lateinit var collectionBtn: Button // 컬렉션 등록 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View

        view = inflater.inflate(R.layout.fragment_my, container, false)

        reviewBtn = view.findViewById(R.id.reviewBtn)
        reviewBtn.setOnClickListener {// 리뷰 등록 페이지로 이동
            val loginIntent = Intent(activity, ReviewActivity::class.java)
            startActivity(loginIntent)
        }

        collectionBtn = view.findViewById(R.id.collectionBtn)
        collectionBtn.setOnClickListener {// 컬렉션 등록 페이지로 이동
            val loginIntent = Intent(activity, CollectionActivity::class.java)
            startActivity(loginIntent)
        }

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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}