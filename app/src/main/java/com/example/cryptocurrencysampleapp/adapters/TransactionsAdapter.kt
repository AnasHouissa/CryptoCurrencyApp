package com.example.cryptocurrencysampleapp.adapters

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencysampleapp.R
import com.example.cryptocurrencysampleapp.database.Database
import com.example.cryptocurrencysampleapp.models.Transaction
import com.example.cryptocurrencysampleapp.models.User
import com.example.cryptocurrencysampleapp.ui.activities.LoginActivity
import com.example.cryptocurrencysampleapp.utils.Constants
import com.example.cryptocurrencysampleapp.utils.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsAdapter(
    private val db: Database,
    private var lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    private var recyclerView: RecyclerView? = null


    inner class TransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)

        return TransactionsViewHolder(view)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id

        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transaction = differ.currentList[position]
        val recept = transaction.recept
        val exp = transaction.expe
        val amount = transaction.amount

        holder.itemView.findViewById<TextView>(R.id.tvAmount).text = amount.toString()
        holder.itemView.findViewById<TextView>(R.id.tvExpName).text = exp
        holder.itemView.findViewById<TextView>(R.id.tvRecepName).text = recept


    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    override fun getItemCount() = differ.currentList.size

    fun addItem(transac: Transaction) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.getTransactionDao().upsertTransaction(transac)
            val pref = SharedPref(recyclerView!!.context.applicationContext)
            val currentUserID = pref.getUser()!!.id
            val currentUser = db.getUserDao().getUserByID(currentUserID)
            currentUser!!.wallet = currentUser.wallet - 1
            db.getUserDao().upsertUser(currentUser)
        }

        val newList = differ.currentList.toMutableList()
        newList.add(transac)
        differ.submitList(newList)
        recyclerView?.postDelayed({
            recyclerView?.scrollToPosition(differ.currentList.size - 1)
        }, 100)

    }

    fun displayAddDialog(transac: Transaction) {
        val builder = AlertDialog.Builder(recyclerView!!.context)
        builder.setTitle("Are you sure you want to create transaction?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            addItem(transac)
        }
        builder.setNegativeButton("No") { _: DialogInterface, _: Int ->

        }

        builder.show()
    }
    /* fun deleteItem(transac: Transaction) {
         db.get.getChampionDao().deleteChamp(champ)
         val newList = differ.currentList.toMutableList()
         newList.remove(champ)
         differ.submitList(newList)
     }*/
}










