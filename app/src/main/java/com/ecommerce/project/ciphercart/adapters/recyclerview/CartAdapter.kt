package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.model.CartData

class CartAdapter():ListAdapter<CartData,CartAdapter.CartViewHolder>(DiffUtilCallBack()) {
    inner class CartViewHolder(itemView:View):ViewHolder(itemView) {

    }
    class DiffUtilCallBack():DiffUtil.ItemCallback<CartData>() {
        override fun areItemsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem.prodId == newItem.prodId
        }

        override fun areContentsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val data = getItem(position)
    }

}