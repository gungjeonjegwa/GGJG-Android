package com.example.ggjg_andorid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.ggjg_andorid.databinding.ItemBannerBinding

class BannerAdapter(private val itemList: List<String>) : PagerAdapter() {
    private lateinit var binding: ItemBannerBinding

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        binding = ItemBannerBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )
        initView(itemList[position])
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun initView(item: String) {
        binding.bannerImg.load(item) {
            transformations(RoundedCornersTransformation(0f))
        }
    }

    override fun getCount(): Int = itemList.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)
}