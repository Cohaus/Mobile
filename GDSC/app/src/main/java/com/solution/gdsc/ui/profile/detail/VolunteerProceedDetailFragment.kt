package com.solution.gdsc.ui.profile.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentVolunteerProceedDetailBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VolunteerProceedDetailFragment : BaseFragment<FragmentVolunteerProceedDetailBinding>(R.layout.fragment_volunteer_proceed_detail) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<VolunteerProceedDetailFragmentArgs>()

    override fun setLayout() {
        viewModel.getRepairsRecord(args.repairId)
        setProceedDetail()
    }

    private fun setProceedDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairRecord.collectLatest {
                    if (it.status in 200..299) binding.repairRecordDto = it.data
                }
            }
        }
    }
}