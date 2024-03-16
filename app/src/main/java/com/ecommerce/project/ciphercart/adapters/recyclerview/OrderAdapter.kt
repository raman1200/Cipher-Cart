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
import com.ecommerce.project.ciphercart.databinding.ProductOngoingitemBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.utils.Constants


class OrderAdapter(private val context: Context, private val orderInterface: OrderInterface,private val s :Boolean=false):
    ListAdapter<CartData, OrderAdapter.OrderViewHolder>(DiffUtilCallBack()) {
    inner class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val binding:ProductOngoingitemBinding
        init {
            binding = ProductOngoingitemBinding.bind(itemView)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_ongoingitem, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.apply {
            data?.let {data ->
                if(s){
                    track.text = "Leave Review"
                    status.text = "Completed"
                }

                prodName.text = data.prodName
                Glide.with(context).load(data.prodImage).placeholder(R.drawable.pic).into(prodImage)
                price.text = Constants.RUPEES_SYMBOL + data.quantity*data.price
                quantity.text = "QTY = ${data.quantity}"

                prod.setOnClickListener {
                    orderInterface.onItemClick(data)
                }
            }}
    }

    interface OrderInterface {
        fun onItemClick(data: CartData)
    }

}