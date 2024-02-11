package com.solution.gdsc.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solution.gdsc.databinding.FragmentDetailMoreDialogBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMoreDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailMoreDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<DetailMoreDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMoreDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout() {
        with(binding) {
            tvDetailRepairApply.setOnClickListener {
                val action = DetailMoreDialogFragmentDirections.actionDetailMoreDialogToHomeLocationSetting(args.savedRecordDto.image)
                findNavController().navigate(action)
            }
            tvDetailRepairApplyInfo.setOnClickListener {
                val action =
                    DetailMoreDialogFragmentDirections.actionDetailMoreDialogToPostRepairApplyInfo()
                findNavController().navigate(action)
            }
            tvDetailEdit.setOnClickListener {
                val action = DetailMoreDialogFragmentDirections.actionDetailMoreDialogToSettingDetailModify(args.savedRecordDto)
                findNavController().navigate(action)
            }
            tvDetailDelete.setOnClickListener {
                viewModel.deleteSavedRecord(args.reocrdId)
                deleteRecord()
            }
        }
    }

    private fun deleteRecord() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteSavedRecord.collectLatest {
                    if (it.status == 200) {
                        val action = DetailMoreDialogFragmentDirections.actionDetailMoreDialogToProfile()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

}