package com.example.cryptocurrencysampleapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.cryptocurrencysampleapp.database.Database
import com.example.cryptocurrencysampleapp.databinding.ActivityLoginBinding
import com.example.cryptocurrencysampleapp.utils.Constants.REMEMBER_ME
import com.example.cryptocurrencysampleapp.utils.Constants.USER_ID

import com.example.cryptocurrencysampleapp.utils.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mainView: ActivityLoginBinding
    private lateinit var userPref: SharedPref
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ActivityLoginBinding.inflate(layoutInflater)
        userPref = SharedPref(this)
        db = Database.getDatabase(this)
        setContentView(mainView.root)

        mainView.btnLogin.setOnClickListener(this)
        mainView.btnRegister.setOnClickListener(this)


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
            mainView.btnLogin -> {
                val email = mainView.etEmail.text.toString().trim()
                val pwd = mainView.etPassword.text.toString()
                val isEmailValid = isEmailValid(email)
                val isPwdValid = isPwdValid(pwd)

                if (isEmailValid && isPwdValid) {

                    GlobalScope.launch(Dispatchers.IO) {
                        val user = db.getUserDao().getUser(email, pwd)

                        if (user == null) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Cannot login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                userPref.saveUser(user)
                                userPref.insertBool(REMEMBER_ME, mainView.cbRememberME.isChecked)
                                finish()
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            }
                        }
                    }


                }
            }
            mainView.btnRegister -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}