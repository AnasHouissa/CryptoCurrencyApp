package com.example.cryptocurrencysampleapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencysampleapp.R
import com.example.cryptocurrencysampleapp.adapters.CurrenciesAdapter
import com.example.cryptocurrencysampleapp.adapters.TransactionsAdapter
import com.example.cryptocurrencysampleapp.database.Database
import com.example.cryptocurrencysampleapp.databinding.FragmentCryptosBinding
import com.example.cryptocurrencysampleapp.models.Currency
import com.example.cryptocurrencysampleapp.models.Transaction
import com.example.cryptocurrencysampleapp.utils.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CryptosFragment : Fragment() {
    private lateinit var mainView: FragmentCryptosBinding
    private lateinit var db: Database
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = FragmentCryptosBinding.inflate(layoutInflater, container, false)
        db = Database.getDatabase(requireContext())
        sharedPref = SharedPref(requireContext())

        setupCurreniesAdapter()
        setupTransactionsAdapter()
        mainView.btnAdd.setOnClickListener {
            val currentUsername = sharedPref.getUser()!!.fullname
            val trans = Transaction("user1", currentUsername, 1)
            transactionsAdapter.displayAddDialog(trans)
        }
        return mainView.root
    }

    private fun setupCurreniesAdapter() {
        val currenciesAdapter = CurrenciesAdapter()
        mainView.rcCurrencies.apply {
            adapter = currenciesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        }
        currenciesAdapter.differ.submitList(getCryptos())
    }

    private fun setupTransactionsAdapter() {
        transactionsAdapter = TransactionsAdapter(db, lifecycleScope)
        mainView.rcTransactions.apply {
            adapter = transactionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        getTransactions()
    }

    private fun getCryptos(): List<Currency> {
        return listOf(
            Currency("BitCoin", R.drawable.bitcoin),
            Currency("BitCoin Cash", R.drawable.bitcoin_cash),
            Currency("Ethereum", R.drawable.ethereum),
            Currency("BnB", R.drawable.bnb),
        )
    }

    private fun getTransactions() {
        lifecycleScope.launch(Dispatchers.IO) {
            val transactions = db.getTransactionDao().getTransactions()
            withContext(Dispatchers.Main) {
                transactionsAdapter.differ.submitList(transactions)
            }
        }


    }


}