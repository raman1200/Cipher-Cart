package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.CheckoutItemsBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.utils.Constants

class CheckoutAdapter(private val context: Context): ListAdapter<CartData, CheckoutAdapter.CheckoutViewHolder>(DiffUtilCallBack()) {

    inner class CheckoutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // binding
        val binding: CheckoutItemsBinding

        init {
            binding = CheckoutItemsBinding.bind(itemView)
        }
    }
    class DiffUtilCallBack(): DiffUtil.ItemCallback<CartData>() {
        override fun areItemsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem.prodId == newItem.prodId
        }

        override fun areContentsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout_items, parent, false)
        return CheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.apply {
            Glide.with(context).load(data.prodImage).placeholder(R.drawable.pic).into(prodImage)
            prodName.text = data.prodName
            price.text = Constants.RUPEES_SYMBOL + data.price*data.quantity
            quantity.text = data.quantity.toString()
        }

    }
}