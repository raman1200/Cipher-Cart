package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ProductItemViewBinding
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.utils.toast

class ProductAdapter(val context: Context, val onClick: OnClick): ListAdapter<ProductData, ProductAdapter.ProductViewHolder>(DiffUtilCallBack()) {

    class ProductViewHolder(itemView: View):ViewHolder(itemView){
        val binding = ProductItemViewBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val data = getItem(position)
        val bind = holder.binding
        Glide.with(context).load(data.images[0]).placeholder(R.drawable.pic).into(bind.prodImage)
        bind.prodTitle.text = data.prodName
        bind.prodPrice.text = data.price.toString()
        bind.ratingTxt.text = data.rating.toString()
        bind.prodItem.setOnClickListener {
            onClick.onItemClick(data) }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.prodId == newItem.prodId
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem == newItem
        }

    }
    interface OnClick {
        fun onItemClick(data: ProductData)
    }
}