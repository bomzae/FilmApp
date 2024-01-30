package com.example.filmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    lateinit var enterID: EditText
    lateinit var enterPwd: EditText
    lateinit var reenterPwd: EditText
    lateinit var registerBtn: Button
    var DB:DBHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        DB = DBHelper(this)

        enterID = findViewById(R.id.editTextText2)
        enterPwd = findViewById(R.id.editTextTextPassword2)
        reenterPwd = findViewById(R.id.editTextTextPassword3)
        registerBtn = findViewById(R.id.button4)

        registerBtn.setOnClickListener {
            var str_id: String = enterID.text.toString()
            var str_pwd: String = enterPwd.text.toString()
            var str_repwd: String = reenterPwd.text.toString()

            if (str_id == "" || str_pwd == "" || str_repwd == "") Toast.makeText(
                this@RegisterActivity,
                "회원 정보를 모두 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show() else {
                if (str_pwd == str_repwd) {
                    val checkUsername = DB!!.checkUsername(str_id)
                    if (checkUsername == false) {
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
                                "비밀번호가 일치하지 않습니다.", // insert가 false일 때면 입력 오류 아닌가?
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "이미 가입된 회원입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
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