package com.example.filmapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "Login.db", null, 1) {
    // 테이블 생성
    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)")
        MyDB.execSQL("create Table film(filmTitle TEXT primary key, director TEXT)")
        MyDB.execSQL("create Table review(username TEXT primary key, filmTitle TEXT, director TEXT, genre TEXT, date DATE, rating FLOAT, review TEXT)")
        MyDB.execSQL("create Table collection(username TEXT primary key, filmTitle TEXT, director TEXT, genre TEXT, date DATE, rating FLOAT, review TEXT)")
    }

    // 테이블 삭제 후 재생성
    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i2: Int) {
        MyDB.execSQL("drop Table if exists users")
        onCreate(MyDB)
    }

    // username, password 정보 DB에 삽입
    fun insertData(username: String?, password: String?): Boolean {
        var MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)
        return if (result == -1L) false else true // 삽입 성공 여부 반환
    }

    // 사용자 입력 감지(입력이 없다면 false)
    fun checkUsername(username: String): Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where username =?", arrayOf(username))
        if (cursor.count <= 0) res = false
        return res
    }

    // password가 입력되었는지 확인(입력이 없다면 false)
    fun checkUserpass(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from users where username = ? and password = ?",
            arrayOf(username, password)
        )
        if (cursor.count <= 0) res = false
        return res
    }

    companion object {
        const val DBNAME = "Login.db"
    }
}