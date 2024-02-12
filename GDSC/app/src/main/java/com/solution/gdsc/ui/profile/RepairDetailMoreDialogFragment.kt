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
import com.solution.gdsc.databinding.FragmentRepairDetailMoreDialogBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepairDetailMoreDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRepairDetailMoreDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<RepairDetailMoreDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepairDetailMoreDialogBinding.inflate(layoutInflater, container, false)
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
            tvDetailRepairApplyInfo.setOnClickListener {
                val action =
                    RepairDetailMoreDialogFragmentDirections.actionRepairDetailMoreToPostRepairApplyInfo(args.repairId)
                findNavController().navigate(action)
            }
            /*tvRepairDetailEdit.setOnClickListener {
                val action = RepairDetailMoreDialogFragmentDirections.actionDetailMoreDialogToSettingDetailModify(args.savedRecordDto)
                findNavController().navigate(action)
            }*/
            tvRepairDetailDelete.setOnClickListener {
                viewModel.deleteSavedRecord(args.repairId)
                deleteRecord()
            }
        }
    }

    private fun deleteRecord() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteSavedRecord.collectLatest {
                    if (it.status == 200) {
                        val action = RepairDetailMoreDialogFragmentDirections.actionRepairDetailMoreToProfile()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}