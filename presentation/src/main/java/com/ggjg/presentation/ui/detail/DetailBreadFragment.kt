package com.ggjg.presentation.ui.detail

import android.content.Intent
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.DetailBannerAdapter
import com.ggjg.presentation.adapter.DetailBreadInfoImgAdapter
import com.ggjg.presentation.databinding.FragmentDetailBreadBinding
import com.ggjg.presentation.ui.agree_notice.ChangeAndRecallActivity
import com.ggjg.presentation.ui.agree_notice.DeliveryAndPayActivity
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.ui.login.LoginActivity
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.heartChange
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.DetailViewModel
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.PayDialogViewModel
import event.ErrorEvent

class DetailBreadFragment :
    BaseFragment<FragmentDetailBreadBinding>(R.layout.fragment_detail_bread) {
    private val detailViewModel by activityViewModels<DetailViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var currentDeliveryView = 0
    private val infoImgAdapter = DetailBreadInfoImgAdapter()
    private var max = 0
    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        PayDialogViewModel.apply {
            breadData = null
            size = null
            breadList = listOf()
        }
        super.onDetach()
    }

    override fun onCreate() {
        repeatOnStart {
            detailViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            detailViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        mainViewModel.hiddenNav(true)
        detailViewModel.detailBread()
        detailViewModel.listReview()
        binding.detailBread = this
        initView()
    }

    private fun handleEvent(event: DetailViewModel.Event) = when (event) {
        is DetailViewModel.Event.DetailBread -> {
            PayDialogViewModel.breadData = event.detailBread
            binding.apply {
                breadData = event.detailBread
                warningTxt.text =
                    if (event.detailBread.deliveryNotice == null) "주의사항이 없습니다" else event.detailBread.deliveryNotice
                if (event.detailBread.precaution != null) {
                    itemInfoTxt.setVisible()
                    itemInfoTxt.text = event.detailBread.precaution
                }
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
                if (event.detailBread.breadImageInfo.isNotEmpty()) {
                    infoImgAdapter.submitList(event.detailBread.breadImageInfo)
                    infoImgList.setVisible()
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
                    DetailBannerAdapter(listOf(event.detailBread.imgUrl).plus(event.detailBread.breadImage))
                max = listOf(event.detailBread.imgUrl).plus(event.detailBread.breadImage).size
                imgIndicator.text = "${imgContainer.currentItem + 1} / $max"
            }
        }
        is DetailViewModel.Event.NoneQa -> {
            binding.noneReview.setVisible(false)
            binding.reviewListLayout.setVisible(false)
            binding.qaListLayout.setVisible(false)
            binding.noneQa.setVisible()
        }
        is DetailViewModel.Event.NoneReview -> {
            binding.reviewListLayout.setVisible(false)
            binding.qaListLayout.setVisible(false)
            binding.noneQa.setVisible(false)
            binding.noneReview.setVisible()
        }
        is DetailViewModel.Event.Qa -> {
            binding.reviewListLayout.setVisible(false)
            binding.noneQa.setVisible(false)
            binding.noneReview.setVisible(false)
            binding.qaListLayout.setVisible()
        }
        is DetailViewModel.Event.Review -> {
            binding.qaListLayout.setVisible(false)
            binding.noneQa.setVisible(false)
            binding.noneReview.setVisible(false)
            binding.reviewListLayout.setVisible()
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

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
        reviewBtn.isActivated = true
        allQaBtn.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        allReviewBtn.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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
        infoImgList.apply {
            this.adapter = infoImgAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.deliveryAndPaymentInfoBtn -> {
                requireActivity().startActivity(
                    Intent(
                        context,
                        DeliveryAndPayActivity::class.java
                    )
                )
            }
            R.id.changeAndRecallInfoBtn -> {
                requireActivity().startActivity(
                    Intent(
                        context,
                        ChangeAndRecallActivity::class.java
                    )
                )
            }
            R.id.likeBtn -> {
                if (MainViewModel.isLogin) {
                    detailViewModel.like()
                    binding.likeBtn.isActivated = !binding.likeBtn.isActivated
                    binding.like.heartChange(binding.likeBtn.isActivated, requireContext())
                }
            }
            R.id.payBtn -> {
                if (MainViewModel.isLogin) {
                    if (PayDialogViewModel.breadData != null) {
                        DetailBreadPayFragment().show(
                            requireActivity().supportFragmentManager,
                            "DetailBreadPayFragment"
                        )
                    }
                } else {
                    this.startActivityForResult(
                        Intent(
                            requireActivity(),
                            LoginActivity::class.java
                        ), 0
                    )
                }
            }
            R.id.reviewBtn -> {
                if (!binding.reviewBtn.isActivated) {
                    binding.reviewBtn.isActivated = true
                    binding.qaBtn.isActivated = false
                    binding.moveQaBtn.setVisible(false)
                    detailViewModel.listReview()
                }
            }
            R.id.qaBtn -> {
                if (!binding.qaBtn.isActivated) {
                    binding.qaBtn.isActivated = true
                    binding.reviewBtn.isActivated = false
                    binding.moveQaBtn.setVisible()
                    detailViewModel.listQa()
                }
            }
            R.id.moveQaBtn -> {
                Toast.makeText(context, "지금은 지원되지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
//                requireActivity().findNavController(R.id.mainContainer)
//                    .navigate(R.id.action_detailBreadFragment_to_QAFragment)
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