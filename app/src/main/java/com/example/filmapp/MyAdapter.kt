package com.example.filmapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: ArrayList<Map<String, Any>>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRank: TextView = itemView.findViewById(R.id.tv_rank)
        val tvMovieNm: TextView = itemView.findViewById(R.id.tv_movieNm)
        val tvOpenDt: TextView = itemView.findViewById(R.id.tv_openDt)

        fun setItem(item: Map<String, Any>) {
            Log.d("json Info.", item.toString())
            tvRank.text = item["rank"].toString()
            tvMovieNm.text = item["movieNm"].toString()
            tvOpenDt.text = item["openDt"].toString()
        }
    }

    // 영화 제목을 지정된 길이와 조건에 맞게 설정하는 메서드
    private fun setMovieTitle(textView: TextView, originalTitle: String) {
        val maxLengthPerLine = 10 // 한 줄당 최대 길이
        val maxLines = 2 // 최대 줄 수
        val maxTotalLength = maxLengthPerLine * maxLines // 전체 최대 길이
        val fontSize = 24 // 기본 폰트 크기
        if (originalTitle.length <= 10) { // 제목이 10자 이하인 경우
            textView.textSize = fontSize.toFloat()
            textView.text = originalTitle
        } else if (originalTitle.length <= maxTotalLength
            && originalTitle.length > 10
        ) { // 제목이 11~20자인 경우
            val firstLine = originalTitle.substring(0, maxLengthPerLine)
            val secondLine =
                originalTitle.substring(maxLengthPerLine, maxLengthPerLine * maxLines)
            val truncatedTitle = """
                    $firstLine
                    $secondLine
                    """.trimIndent()
            textView.textSize = 19f
            textView.text = truncatedTitle
        } else {
            // 21자 이상인 경우
            val firstLine = originalTitle.substring(0, maxLengthPerLine)
            val secondLine =
                originalTitle.substring(maxLengthPerLine, maxLengthPerLine * maxLines)
            val truncatedTitle = "$firstLine\n$secondLine..."
            textView.textSize = 19f
            textView.text = truncatedTitle
        }
    }
}