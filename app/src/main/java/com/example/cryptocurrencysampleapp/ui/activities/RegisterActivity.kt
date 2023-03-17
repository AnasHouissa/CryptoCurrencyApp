package com.example.cryptocurrencysampleapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.cryptocurrencysampleapp.database.Database
import com.example.cryptocurrencysampleapp.databinding.ActivityRegisterBinding
import com.example.cryptocurrencysampleapp.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG="RegisterActivity"
    private lateinit var mainView: ActivityRegisterBinding
    private lateinit var db: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ActivityRegisterBinding.inflate(layoutInflater)
        db = Database.getDatabase(this)
        setContentView(mainView.root)

        mainView.btnBack.setOnClickListener(this)
        mainView.btnRegister.setOnClickListener(this)
    }


    private fun isFullnameValid(fullname: String): Boolean {
        if (fullname.isEmpty()) {
            mainView.tlFullName.apply {
                error = "Fullname cannot be empty"
                isErrorEnabled = true
            }
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            mainView.tlEmail.apply {
                error = "Invalid Email"
                isErrorEnabled = true
            }
            return false
        }
        return true
    }

    private fun isPwdValid(pwd: String): Boolean {
        if (pwd.isEmpty() || pwd.length < 8) {
            mainView.tlPassword.apply {
                error = "Your password must be at least 8 characters long"
                isErrorEnabled = true
            }
            return false
        }
        return true
    }



    override fun onClick(v: View?) {
        when (v) {
            mainView.btnBack -> finish()
            mainView.btnRegister -> {

                val fullname = mainView.etFullName.text.toString()
                val email = mainView.etEmail.text.toString().trim()
                val pwd = mainView.etPassword.text.toString()

                val isValidFullname = isFullnameValid(fullname)
                val isValidEmail = isEmailValid(email)
                val isValidPwd = isPwdValid(pwd)

                if (isValidFullname && isValidEmail && isValidPwd) {

                    GlobalScope.launch(Dispatchers.Main) {
                        val user = User(email, pwd, fullname, 100)
                        withContext(Dispatchers.IO) {
                           db.getUserDao().upsertUser(user)
                        }
                        finish()
                    }

                }

            }
        }
    }
}