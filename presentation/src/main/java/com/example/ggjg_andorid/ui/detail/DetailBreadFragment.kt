package com.example.ggjg_andorid.ui.detail

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.BannerAdapter
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.databinding.FragmentDetailBreadBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.ui.main.MainActivity
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled
import com.example.ggjg_andorid.utils.heartChange
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.HomeViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

class DetailBreadFragment :
    BaseFragment<FragmentDetailBreadBinding>(R.layout.fragment_detail_bread) {
    private val detailViewModel by activityViewModels<DetailViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var currentDeliveryView = 0
    private var max = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BaseActivity<ActivityMainBinding>).setOnKeyBackPressedListener(object :
            BaseActivity.OnKeyBackPressedListener {
            override fun onBack() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@DetailBreadFragment).commit()
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.hiddenNav(false)
    }

    override fun createView() {
        binding.detailBread = this
        mainViewModel.hiddenNav(true)
        detailViewModel.detailBread()
        initView()
        repeatOnStart {
            detailViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: DetailViewModel.Event) = when (event) {
        is DetailViewModel.Event.IsLogin -> {
            if (!event.isLogin) {
                this.startActivityForResult(
                    Intent(
                        requireActivity(),
                        LoginActivity::class.java
                    ), 0
                )
            } else {
                DetailBreadPayFragment().show(
                    requireActivity().supportFragmentManager,
                    "DetailBreadPayFragment"
                )
            }
        }
        is DetailViewModel.Event.DetailBread -> {
            PayViewModel.breadData = event.detailBread
            binding.apply {
                breadData = event.detailBread
                if (event.detailBread.isSoldOut) {
                    payBtn.changeActivatedWithEnabled(false)
                    payBtn.text = getString(R.string.sold_out_korean)
                }
                if (event.detailBread.price == "매장판매") {
                    costTxt.text = getString(R.string.sell_shop)
                    wonTxt.visibility = View.GONE
                    payBtn.changeActivatedWithEnabled(false)
                    payBtn.text = getString(R.string.sell_shop)
                }
                if (event.detailBread.size != null) {
                    size.setVisible()
                    sizeTxt.setVisible()
                }
                binding.likeBtn.isActivated = event.detailBread.isLike
                binding.like.isActivated = event.detailBread.isLike
                event.detailBread.deliveryType.forEach {
                    when (it.sellType) {
                        "GWANGJU" -> {
                            viewDeliveryType(R.drawable.bg_gwangju)
                        }
                        "BAEMIN" -> {
                            viewDeliveryType(R.drawable.bg_baemin)
                        }
                        else -> {
                            viewDeliveryType(R.drawable.bg_all_contry)
                        }
                    }
                }
                binding.imgContainer.adapter =
                    BannerAdapter(listOf(event.detailBread.imgUrl).plus(event.detailBread.breadImage))
                max = listOf(event.detailBread.imgUrl).plus(event.detailBread.breadImage).size
                imgIndicator.text = "${imgContainer.currentItem + 1} / $max"
            }
        }
    }

    private fun viewDeliveryType(view: Int) {
        if (currentDeliveryView == 0) {
            binding.deliveryOption1.setBackgroundResource(view)
        } else {
            binding.deliveryOption2.setBackgroundResource(view)
        }
    }

    private fun initView() = binding.apply {
        payBtn.changeActivatedWithEnabled(true)
        val height = (requireContext().resources.displayMetrics.heightPixels * 0.45).toInt()
        imgContainer.run {
            layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) = Unit

                override fun onPageScrollStateChanged(state: Int) =
                    Unit

                override fun onPageSelected(position: Int) {
                    imgIndicator.text = "${imgContainer.currentItem + 1} / $max"
                }
            })
        }
        infoScrollView.run {
            header = tabLayout
            item = imgContainer
            itemOverListener = {
                tabLayout.setBackgroundColor(requireActivity().getColor(R.color.background))
            }
            itemUnOverListener = {
                tabLayout.setBackgroundColor(requireActivity().getColor(R.color.transparent))
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            R.id.deliveryAndPaymentInfoBtn -> {
                requireActivity().startActivity(
                    Intent(
                        context,
                        DetailDeliveryAndPayActivity::class.java
                    )
                )
            }
            R.id.changeAndRecallInfoBtn -> {
                requireActivity().startActivity(
                    Intent(
                        context,
                        DetailChangeAndRecallActivity::class.java
                    )
                )
            }
            R.id.likeBtn -> {
                if (MainViewModel.isLogin) {
                    binding.likeBtn.isActivated = !binding.likeBtn.isActivated
                    binding.like.heartChange(binding.likeBtn.isActivated, requireContext())
                }
            }
            R.id.payBtn -> {
                detailViewModel.isLogin()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            DetailBreadPayFragment().show(
                requireActivity().supportFragmentManager,
                "DetailBreadPayFragment"
            )
        }
    }
}