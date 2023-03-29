package com.example.firebaseremoteconfig

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class QuotePagerAdapter(
   private val quotes: ArrayList<Quote>,
   private val isNameRevealed : Boolean
) : RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.itme_quote, parent, false)
    )

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(quotes[position % quotes.size],isNameRevealed)
    }

    class QuoteViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        private val quoteTV : TextView = itemview.findViewById(R.id.quoteTv)
        private val nameTV : TextView = itemview.findViewById(R.id.nameTV)

        fun bind(quote : Quote, isNameRevealed: Boolean){
            quoteTV.text = quote.quote
            if (isNameRevealed) {
                nameTV.text = quote.name
                nameTV.visibility = View.VISIBLE
            } else {
                nameTV.visibility = View.GONE
            }
        }
    }
}