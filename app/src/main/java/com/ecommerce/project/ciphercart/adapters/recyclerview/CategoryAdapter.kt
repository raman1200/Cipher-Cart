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
import com.ecommerce.project.ciphercart.databinding.CategoryItemViewBinding
import com.ecommerce.project.ciphercart.model.CategoryData

class CategoryAdapter(val context: Context): ListAdapter<CategoryData, CategoryAdapter.CategoryViewHolder>(DiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_view, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = getItem(position)
        val bd = holder.binding

        bd.name.text = data.catName
        Glide.with(context).load(data.catImage).placeholder(R.drawable.person).into(bd.image)
    }


    inner class CategoryViewHolder(itemView: View):ViewHolder(itemView) {
        val binding = CategoryItemViewBinding.bind(itemView)
    }
    class DiffUtilCallBack: DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.catId == newItem.catId
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }
    }
}