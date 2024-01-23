package com.solution.gdsc.ui.home.save

import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRecordSaveBinding

class HomeRecordSaveFragment : BaseFragment<FragmentHomeRecordSaveBinding>(R.layout.fragment_home_record_save) {
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
                val action = HomeRecordSaveFragmentDirections.actionRecordSaveToHome()
                findNavController().navigate(action)
            }
        }
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
}