package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostDetailBinding

class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {
    override fun setLayout() {
        binding.ibBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}