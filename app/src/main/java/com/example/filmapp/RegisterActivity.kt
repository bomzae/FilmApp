package com.example.filmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    lateinit var enterID: EditText // 아이디 입력 텍스트
    lateinit var enterPwd: EditText // 패스워드 입력 텍스트
    lateinit var reenterPwd: EditText // 패스워드 재입력 텍스트
    lateinit var registerBtn: Button // 회원가입 버튼
    var DB:DBHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        DB = DBHelper(this)

        enterID = findViewById(R.id.idEditText)
        enterPwd = findViewById(R.id.passEditText)
        reenterPwd = findViewById(R.id.repassEditText)
        registerBtn = findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {
            var str_id: String = enterID.text.toString()
            var str_pwd: String = enterPwd.text.toString()
            var str_repwd: String = reenterPwd.text.toString()

            // 세 텍스트에 하나라도 입력이 비어있는 경우
            if (str_id == "" || str_pwd == "" || str_repwd == "") Toast.makeText(
                this@RegisterActivity,
                "회원 정보를 모두 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show() else {
                if (str_pwd == str_repwd) { // 비밀번호와 비밀번호 재입력이 같은 경우
                    val checkUsername = DB!!.checkUsername(str_id) // DB에서 이미 있는 유저와 아이디가 겹치는지 체크
                    if (checkUsername == false) { // 아이디가 겹치지 않으면 DB에 등록, 가입 성공
                        val insert = DB!!.insertData(str_id, str_pwd)
                        if (insert == true) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "가입되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "비밀번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else { // 이미 있는 아이디인 경우
                        Toast.makeText(
                            this@RegisterActivity,
                            "이미 가입된 회원입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else { // 비밀번호와 비밀번호 재입력이 다른 경우
                    Toast.makeText(
                        this@RegisterActivity,
                        "비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }
}