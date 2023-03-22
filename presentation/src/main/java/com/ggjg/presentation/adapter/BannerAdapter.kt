package com.ggjg.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.ggjg.domain.entity.bread.BannerEntity
import com.ggjg.presentation.databinding.ItemBannerBinding

class BannerAdapter(private val itemList: List<BannerEntity>, private val context: Context) : PagerAdapter() {
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

    private fun initView(item: BannerEntity) {
        binding.bannerImg.load(item.imgUrl) {
            transformations(RoundedCornersTransformation(0f))
        }
        binding.bannerImg.setOnClickListener {
            if (item.webUrl != null) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.webUrl)))
            }
        }
    }

    override fun getCount(): Int = itemList.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)
}