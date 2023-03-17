package com.example.cryptocurrencysampleapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptocurrencysampleapp.database.Database
import com.example.cryptocurrencysampleapp.databinding.FragmentWalletBinding
import com.example.cryptocurrencysampleapp.utils.Constants
import com.example.cryptocurrencysampleapp.utils.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WalletFragment : Fragment() {

    private lateinit var mainView: FragmentWalletBinding
    private lateinit var db: Database
    private lateinit var sharedPref: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = FragmentWalletBinding.inflate(layoutInflater, container, false)
        db= Database.getDatabase(requireContext())
        sharedPref= SharedPref(requireContext())
        getUserDetails()
        return mainView.root
    }

    private fun getUserDetails() {
       lifecycleScope.launch(Dispatchers.IO){
           val userId=sharedPref.getUser()!!.id
           val user=db.getUserDao().getUserByID(userId)
           sharedPref.saveUser(user!!)
           withContext(Dispatchers.Main){
               mainView.tvWallet.text=user.wallet.toString()
               mainView.tvEmail.text= user.email
               mainView.tvName.text= user.fullname

               mainView.pi.visibility=View.GONE
               mainView.mainLayout.visibility=View.VISIBLE

           }
       }
    }

}