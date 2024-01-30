package com.example.filmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var editID: EditText // 아이디 입력
    lateinit var editPwd: EditText // 패스워드 입력
    lateinit var loginBtn: Button // 로그인 버튼
    lateinit var registerBtn: Button // 회원가입 버튼
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

            // 두 텍스트에 빈 텍스트가 있는 경우
            if(str_id == "" || str_pwd == "") Toast.makeText(
                this@MainActivity,
                "회원 정보를 모두 입력해주세요",
                Toast.LENGTH_SHORT
            ).show() else { // 두 텍스트 모두 입력이 있는 경우
                val checkUserPass = DB!!.checkUserpass(str_id, str_pwd) // DB에 있는 아이디, 비밀번호인지 체크
                if (checkUserPass == true) { // 아이디, 비밀번호가 모두 확인되면 로그인 성공
                    Toast.makeText(
                        this@MainActivity,
                        "로그인 되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                } else { // 아이디나 비밀번호가 일치하지 않으면 로그인 실패
                    Toast.makeText(
                        this@MainActivity,
                        "회원 정보가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            registerBtn.setOnClickListener {// 회원가입 페이지로 이동
                val loginIntent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(loginIntent)
            }
        }
    }
}