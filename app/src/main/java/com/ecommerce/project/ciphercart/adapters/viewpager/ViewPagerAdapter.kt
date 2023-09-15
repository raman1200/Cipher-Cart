package com.ecommerce.project.ciphercart.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ecommerce.project.ciphercart.fragments.shopping.CompletedFragment
import com.ecommerce.project.ciphercart.fragments.shopping.OngoingFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return  when(position){
            0 -> {
                OngoingFragment()
            }
            1 -> {
                CompletedFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}