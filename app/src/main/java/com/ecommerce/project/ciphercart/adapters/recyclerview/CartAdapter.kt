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
import com.ecommerce.project.ciphercart.databinding.ProductCartItemBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.toast
import com.mcdev.quantitizerlibrary.AnimationStyle
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer

class CartAdapter(private val context: Context, private val deleteInterface: DeleteInterface):ListAdapter<CartData,CartAdapter.CartViewHolder>(DiffUtilCallBack()) {

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
        holder.binding.apply {
            prodName.text = data.prodName
            price.text = Constants.RUPEES_SYMBOL + data.price.toString()
            Glide.with(context).load(data.prodImage).placeholder(R.drawable.pic).into(prodImage)
            setUpQuantizer(hQ)
            hQ.value = data.quantity

            delete.setOnClickListener {
                deleteInterface.deleteItem(data.prodId)
            }
        }
    }

    private fun setUpQuantizer(hQ:HorizontalQuantitizer) {
        hQ.setPlusIconBackgroundColor("#00FFFFFF")
        hQ.setMinusIconBackgroundColor("#00FFFFFF")
        hQ.setMinusIconColor("#FF000000")
        hQ.setPlusIconColor("#FF000000")
        hQ.setValueTextColor("#FF000000")
        hQ.minValue = 1
        hQ.buttonAnimationEnabled = false
        hQ.textAnimationStyle = AnimationStyle.FALL_IN
    }

    interface DeleteInterface {
        fun deleteItem(id:String)
    }

}

