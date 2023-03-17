package com.example.cryptocurrencysampleapp.ui.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cryptocurrencysampleapp.R
import com.example.cryptocurrencysampleapp.databinding.ActivityHomeBinding
import com.example.cryptocurrencysampleapp.ui.fragments.CryptosFragment
import com.example.cryptocurrencysampleapp.ui.fragments.WalletFragment
import com.example.cryptocurrencysampleapp.utils.Constants.REMEMBER_ME
import com.example.cryptocurrencysampleapp.utils.SharedPref

class HomeActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mainView: ActivityHomeBinding
    private lateinit var userPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = ActivityHomeBinding.inflate(layoutInflater)
        userPref = SharedPref(this)

        setContentView(mainView.root)

        setupToolbar()
        replaceFragment(CryptosFragment())
        mainView.btnWallet.setOnClickListener(this)
        mainView.btnCryptos.setOnClickListener(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(mainView.tb)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Home"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.logout -> {
                displayLogoutDialog()
                true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    private fun displayLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            userPref.removeBool(REMEMBER_ME)
            startActivity(Intent(this, LoginActivity::class.java))
        }
        builder.setNegativeButton("No") { _: DialogInterface, _: Int ->

        }

        builder.show()
    }

    override fun onClick(v: View?) {
        when (v) {
            mainView.btnCryptos -> replaceFragment(CryptosFragment())
            mainView.btnWallet -> replaceFragment(WalletFragment())
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(mainView.fl.id, fragment).commit()
    }
}