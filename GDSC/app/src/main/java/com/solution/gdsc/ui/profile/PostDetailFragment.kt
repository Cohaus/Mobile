package com.solution.gdsc.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostDetailBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {
    private val args by navArgs<PostDetailFragmentArgs>()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun setLayout() {
        viewModel.getSaveRecordInfo(args.recordItem.recordId)
        binding.ibBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ibMoreButton.setOnClickListener {
            val action = PostDetailFragmentDirections.actionPostDetailToDetailMoreDialog()
            findNavController().navigate(action)
        }
        binding.progressBarSafeGrade.setProgress(90f)
        setRecordInfo()
    }

    private fun setRecordInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedRecordInfo.collectLatest {
                    binding.savedRecordInfo = it
                }
            }
        }
    }
}