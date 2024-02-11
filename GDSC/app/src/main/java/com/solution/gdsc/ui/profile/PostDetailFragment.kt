package com.solution.gdsc.ui.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostDetailBinding
import com.solution.gdsc.domain.model.response.SavedRecordDto
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {
    private val args by navArgs<PostDetailFragmentArgs>()
    private val viewModel by viewModels<ProfileViewModel>()
    private var savedRecordDto: SavedRecordDto? = null

    override fun setLayout() {
        viewModel.getSaveRecordInfo(args.recordItem.recordId)
        binding.ibBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ibMoreButton.setOnClickListener {
            if (savedRecordDto != null) {
                val action = PostDetailFragmentDirections.actionPostDetailToDetailMoreDialog(
                    args.recordItem.recordId, savedRecordDto!!
                )
                findNavController().navigate(action)
            }
        }
        binding.progressBarSafeGrade.setProgress(90f)
        setRecordInfo()
    }

    private fun setRecordInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedRecordInfo.collectLatest {
                    savedRecordDto = it
                    binding.savedRecordInfo = savedRecordDto
                    setProgress(it.grade)
                }
            }
        }
    }

    private fun setProgress(grade: String) {
        with(binding) {
            when (grade) {
                // to 등급에 따른 텍스트 변경 do
                "우수" -> {
                    progressBarSafeGrade.setProgress(100f)
                    groupAiResult.visibility = View.VISIBLE
                }
                "보통" -> {
                    binding.progressBarSafeGrade.setProgress(50f)
                    groupAiResult.visibility = View.VISIBLE
                }
                "불량" -> {
                    binding.progressBarSafeGrade.setProgress(30f)
                    groupAiResult.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBarSafeGrade.setProgress(0f)
                    groupAiResult.visibility = View.GONE
                }
            }
        }
    }
}

