package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ProductItemViewBinding
import com.ecommerce.project.ciphercart.model.ProductData

class ProductAdapter(val context: Context): ListAdapter<ProductData, ProductAdapter.ProductViewHolder>(DiffUtilCallBack()) {

    class ProductViewHolder(itemView: View):ViewHolder(itemView){
        val binding = ProductItemViewBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.prodId == newItem.prodId
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem == newItem
        }

    }
}