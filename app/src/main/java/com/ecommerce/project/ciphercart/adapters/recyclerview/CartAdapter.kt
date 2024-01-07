package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ProductCartItemBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.mcdev.quantitizerlibrary.AnimationStyle

class CartAdapter():ListAdapter<CartData,CartAdapter.CartViewHolder>(DiffUtilCallBack()) {

    inner class CartViewHolder(itemView:View):ViewHolder(itemView) {
        // binding
        val binding:ProductCartItemBinding

        init {
            binding = ProductCartItemBinding.bind(itemView)
        }
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
        // bind the data
        val bd = holder.binding
        bd.prodName.text = data.prodName
        bd.price.text = data.price.toString()
        bd.hQ.setOnClickListener {
                bd.hQ.setPlusIconBackgroundColor("#00FFFFFF")
                bd.hQ.setMinusIconBackgroundColor("#00FFFFFF")
                bd.hQ.setMinusIconColor("#FF000000")
                bd.hQ.setPlusIconColor("#FF000000")
                bd.hQ.setValueTextColor("#FF000000")
                bd.hQ.minValue = 1
                bd.hQ.buttonAnimationEnabled = false
                bd.hQ.textAnimationStyle = AnimationStyle.FALL_IN
            }
        }


    }

