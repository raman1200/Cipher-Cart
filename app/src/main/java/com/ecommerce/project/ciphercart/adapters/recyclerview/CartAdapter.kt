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
import com.google.android.play.core.integrity.p
import com.mcdev.quantitizerlibrary.AnimationStyle
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartAdapter(private val context: Context, private val cartInterface: CartInterface):ListAdapter<CartData,CartAdapter.CartViewHolder>(DiffUtilCallBack()) {
    inner class CartViewHolder(itemView:View):ViewHolder(itemView) {
        // binding
        val binding: ProductCartItemBinding

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
            data?.let {data ->
            prodName.text = data.prodName
            price.text = Constants.RUPEES_SYMBOL + data.price.toString()
            Glide.with(context).load(data.prodImage).placeholder(R.drawable.pic).into(prodImage)
            setUpQuantizer(hQ)
            hQ.value = data.quantity
            price.text = Constants.RUPEES_SYMBOL + data.quantity*data.price
            var cp = hQ.value*data.price
            hQ.setQuantitizerListener(object :QuantitizerListener{
                override fun onDecrease() {}
                override fun onIncrease() {}
                override fun onValueChanged(value: Int) {
                    currentList[holder.bindingAdapterPosition].quantity = value
                    val p = (value*data.price)
                    price.text = Constants.RUPEES_SYMBOL + p
                    val s = if(cp>p){
                        -data.price
                    }else{
                        data.price
                    }
                    cartInterface.hQValue(s, data, value)
                    cp = p
                }
            })

            delete.setOnClickListener {
                cartInterface.deleteItem(data.prodId)
            }
            prodItem.setOnClickListener {
                cartInterface.onCartItemClick(data)
            }
        }}
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

    interface CartInterface {
        fun deleteItem(id:String)
        fun onCartItemClick(data:CartData)
        fun hQValue(price:Double, cartData: CartData, value:Int)
    }

}

