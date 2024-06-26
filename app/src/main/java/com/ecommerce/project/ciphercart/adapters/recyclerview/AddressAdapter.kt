package com.ecommerce.project.ciphercart.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.AddressListBinding
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.model.CartData

class AddressAdapter(private val addressInterface: AddressInterface, private val source:Boolean = false):ListAdapter<AddressData,AddressAdapter.AddressViewHolder>(DiffUtilCallBack()) {
    inner class AddressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = AddressListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_list, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.apply{
            title.text = data.nameAddress
            address.text = data.address
            if(data.defaultAddress)
                deft.visibility = View.VISIBLE

            if(source){
                edit.visibility = View.INVISIBLE
            }
            edit.setOnClickListener {
                addressInterface.updateAddress(data)
            }
        }
    }
    class DiffUtilCallBack(): DiffUtil.ItemCallback<AddressData>() {
        override fun areItemsTheSame(oldItem: AddressData, newItem: AddressData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AddressData, newItem: AddressData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface AddressInterface {
        fun updateAddress(data: AddressData)

    }
}