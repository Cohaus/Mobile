package com.solution.gdsc.ui.home.save

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRecordSaveBinding
import com.solution.gdsc.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeRecordSaveFragment :
    BaseFragment<FragmentHomeRecordSaveBinding>(R.layout.fragment_home_record_save) {
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
    }

    private fun saveRecord() {
        //val category = if (args.category == null) "" else args.category
        viewModel.saveRecord(inputTitle, inputContent, "보통", "CRACK", args.image)
        complete()
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

    private fun complete() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveResult.collectLatest {
                    if (it.status in 200..299) {
                        val action = HomeRecordSaveFragmentDirections.actionRecordSaveToHome()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}