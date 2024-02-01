package com.solution.gdsc.ui.home.save

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRecordSaveBinding
import com.solution.gdsc.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeRecordSaveFragment : BaseFragment<FragmentHomeRecordSaveBinding>(R.layout.fragment_home_record_save) {
    private val viewModel by viewModels<HomeViewModel>()
    private val args by navArgs<HomeRecordSaveFragmentArgs>()

    private var inputTitle = ""
    private var inputContent = ""

    private var isValidTitle = false
    private var isValidContent = false

    override fun setLayout() {
        setTextInput()
        with(binding) {
            toolbarRecordSave.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            btnRecordSave.setOnClickListener {
                saveRecord()
            }
        }
        observe()
    }

    private fun saveRecord() {
        viewModel.saveRecord(inputTitle, inputContent, "보통", "도배", args.image)
    }

    private fun setTextInput() {
        with(binding) {
            etRecordInputTitle.doAfterTextChanged {
                inputTitle = it?.toString() ?: ""
                isValidTitle = isValidInput(inputTitle)
                updateButtonEnableState()

            }
            etRecordInputContent.doAfterTextChanged {
                inputContent = it?.toString() ?: ""
                isValidContent = isValidInput(inputContent)
                updateButtonEnableState()
            }
        }
    }


    private fun isValidInput(text: String): Boolean = text.isNotBlank()

    private fun updateButtonEnableState() {
        binding.btnRecordSave.isEnabled = isValidTitle && isValidContent
    }

    private fun observe() {
        viewModel.saveResult.observe(viewLifecycleOwner) {
            if (it.status in 200..299) {
                val action = HomeRecordSaveFragmentDirections.actionRecordSaveToHome()
                findNavController().navigate(action)
            }
        }
    }
}