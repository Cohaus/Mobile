package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostDetailBinding

class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {
    private val args by navArgs<PostDetailFragmentArgs>()

    override fun setLayout() {
        val detail = args.recordItem
        binding.ibBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ibMoreButton.setOnClickListener {
            val action = PostDetailFragmentDirections.actionPostDetailToDetailMoreDialog()
            findNavController().navigate(action)
        }
        binding.progressBarSafeGrade.setProgress(90f)
        binding.tvPostTitle.text = detail.title
        binding.tvPostDate.text = detail.createdAt
    }
}