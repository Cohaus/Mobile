package com.solution.gdsc.ui.profile.detail

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentDetailReocrdModifyBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailRecordModifyFragment : BaseFragment<FragmentDetailReocrdModifyBinding>(R.layout.fragment_detail_reocrd_modify) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<DetailRecordModifyFragmentArgs>()

    private var inputTitle = ""
    private var inputContent = ""

    private var isValidTitle = false
    private var isValidContent = false

    override fun setLayout() {
        setTextInput()
        binding.repairRecord = args.repairRecordDto
        with(binding) {
            toolbarDetailRecordEdit.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            btnDetailEditRecordSave.setOnClickListener {
                viewModel.updateSavedRecord(args.repairRecordDto.recordId, inputTitle, inputContent)
                updateSavedRecord()
            }
        }
    }

    private fun setTextInput() {
        with(binding) {
            etDetailEditRecordInputTitle.doAfterTextChanged {
                inputTitle = it?.toString() ?: ""
                isValidTitle = isValidInput(inputTitle)
                updateButtonEnableState()

            }
            etDetailEditRecordInputContent.doAfterTextChanged {
                inputContent = it?.toString() ?: ""
                isValidContent = isValidInput(inputContent)
                updateButtonEnableState()
            }
        }
    }


    private fun isValidInput(text: String): Boolean = text.isNotBlank()

    private fun updateButtonEnableState() {
        binding.btnDetailEditRecordSave.isEnabled = isValidTitle && isValidContent
    }

    private fun updateSavedRecord() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.updateSavedRecord.collectLatest {
                    if (it == 200) {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}