package com.example.cryptocurrencysampleapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencysampleapp.R
import com.example.cryptocurrencysampleapp.models.Currency
import org.w3c.dom.Text

class CurrenciesAdapter  : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currencies, parent, false)

        return CurrencyViewHolder(view)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.image == newItem.image

        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = differ.currentList[position]
        val name = currency.name
        val image = currency.image

        holder.itemView.findViewById<ImageView>(R.id.ivCurrency).setImageResource(image)
        holder.itemView.findViewById<TextView>(R.id.tvCurrencyName).text=name


    }



    override fun getItemCount() = differ.currentList.size


}