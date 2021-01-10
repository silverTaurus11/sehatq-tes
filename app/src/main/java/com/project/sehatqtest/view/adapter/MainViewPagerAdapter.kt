package com.project.sehatqtest.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.sehatqtest.view.frontview.menu.home.HomeFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity)  {

    val menuList by lazy { listOf(HomeFragment()) }

    override fun getItemCount(): Int = menuList.size

    override fun createFragment(position: Int): Fragment {
        return menuList[position]
    }

    inline fun <reified T>getFragmentPosition(): Int{
        for(index in 0..this.menuList.size){
            if(this.menuList[index] is T){
                return index
            }
        }
        return 0
    }
}