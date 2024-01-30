package com.example.filmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var editID: EditText
    lateinit var editPwd: EditText
    lateinit var loginBtn: Button
    lateinit var registerBtn: Button
    var DB: DBHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DB = DBHelper(this)

        editID = findViewById(R.id.idEditText)
        editPwd = findViewById(R.id.passEditText)
        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.registerBtn)

        loginBtn.setOnClickListener {
            var str_id: String = editID.text.toString()
            var str_pwd: String = editPwd.text.toString()

            if(str_id == "" || str_pwd == "") Toast.makeText(
                this@MainActivity,
                "회원 정보를 모두 입력해주세요",
                Toast.LENGTH_SHORT
            ).show() else {
                val checkUserPass = DB!!.checkUserpass(str_id, str_pwd)
                if (checkUserPass == true) {
                    Toast.makeText(
                        this@MainActivity,
                        "로그인 되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "회원 정보가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            registerBtn.setOnClickListener {
                val loginIntent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(loginIntent)
            }
        }
    }
}