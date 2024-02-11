package com.solution.gdsc.ui.home

import android.text.InputFilter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRepairApplyBinding
import com.solution.gdsc.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val DATE_FORMAT_DELIMITER = " / "
private const val DATE_FORMAT_DELETE_DELIMITER = " /"
private const val DATE_MAX_FORMAT_LENGTH = 14

@AndroidEntryPoint
class HomeRepairApplyFragment : BaseFragment<FragmentHomeRepairApplyBinding>(R.layout.fragment_home_repair_apply) {
    private val viewModel by viewModels<HomeViewModel>()
    private val args: HomeRepairApplyFragmentArgs by navArgs()

    private var validDate = ""
    private var validTitle = ""
    private var validContent = ""

    private var isValidTitle = false
    private var isValidContent = false
    private var isValidDate = false

    override fun setLayout() {
        binding.btnRepairApplySave.setOnClickListener {
            viewModel.postRepairBasicRecord(
                validTitle, validContent, "CRACK", args.placeId, args.address,
                args.district, validDate.replace(DATE_FORMAT_DELIMITER, "-"), args.image
            )
        }
        binding.toolbarRepairApply.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setTextInput()
        observe()
    }

    private fun setTextInput() {
        setDayMonthFormat()
        with(binding) {
            etRepairApplyTitle.doAfterTextChanged {
                validTitle = it.toString()
                if (isValidInput(validTitle)) {
                    isValidTitle = true
                    updateButtonEnable()
                } else {
                    isValidTitle = false
                    updateButtonEnable()
                }
            }
            etRepairApplyContent.doAfterTextChanged {
                validContent = it.toString()
                if (isValidInput(validContent)) {
                    isValidContent = true
                    updateButtonEnable()
                } else {
                    isValidContent = false
                    updateButtonEnable()
                }
            }
        }
    }

    private fun isValidInput(text: String) = text.isNotEmpty()

    private fun updateButtonEnable() {
        binding.btnRepairApplySave.isEnabled = isValidTitle && isValidContent && isValidDate
    }

    private fun setDayMonthFormat() {
        setMaxLength()
        with(binding) {
            etPreferVisitDate.doAfterTextChanged {
                val visitDate = it?.toString()
                when {
                    visitDate.isNullOrBlank() -> return@doAfterTextChanged
                    isValidDelete(visitDate, 6) -> {
                        applyDeleteFormat(visitDate, 3)
                    }
                    visitDate.length == 4 && !visitDate.contains(DATE_FORMAT_DELIMITER) -> {
                        setMonthMaxInput()
                        applyDateFormat(visitDate, 4, 7)
                    }
                    isValidDelete(visitDate, 11) -> {
                        applyDeleteFormat(visitDate, 8)
                    }
                    visitDate.length == 9 -> {
                        setDayMaxInput()
                        applyDateFormat(visitDate, 9, 12)
                    }
                }
                validDate = visitDate.toString()
                if (validDate.length == DATE_MAX_FORMAT_LENGTH)  {
                    isValidDate = true
                    updateButtonEnable()
                } else {
                    isValidDate = false
                    updateButtonEnable()
                }
            }
        }
    }


    private fun setMonthMaxInput() {
        with(binding) {
            etPreferVisitDate.doAfterTextChanged {
                val visitDate = it?.toString()
                if (visitDate.isNullOrBlank()) return@doAfterTextChanged
                if (visitDate.length < 9 || visitDate.length >= 10) return@doAfterTextChanged
                val month = visitDate.substring(7, 9).toInt()
                val etc = visitDate.substring(0, 7)
                if (month <= 0 || month > 12 ) {
                    val result = "${etc}12"
                    etPreferVisitDate.setText(result)
                    etPreferVisitDate.setSelection(12)
                }
            }
        }
    }

    private fun setDayMaxInput() {
        with(binding) {
            etPreferVisitDate.doAfterTextChanged {
                val visitDate = it?.toString()
                if (visitDate.isNullOrBlank()) return@doAfterTextChanged
                if (visitDate.length < 14) return@doAfterTextChanged
                val split = visitDate.split(" / ")
                val day = split[2].toInt()
                if (day <= 0 || day > 31) {
                    val result = "${split[0]}$DATE_FORMAT_DELIMITER${split[1]} / ${31}"
                    etPreferVisitDate.setText(result)
                    etPreferVisitDate.setSelection(14)
                }
            }
        }
    }

    private fun isValidDelete(text: String, length: Int): Boolean {
        return text.length == length && text.contains(DATE_FORMAT_DELETE_DELIMITER)
    }

    private fun applyDateFormat(text: String, changeIndex: Int, newStartIndex: Int) {
        val result = text.substring(0, changeIndex) + DATE_FORMAT_DELIMITER
        binding.etPreferVisitDate.setText(result)
        binding.etPreferVisitDate.setSelection(newStartIndex)
    }

    private fun applyDeleteFormat(text: String, changeIndex: Int) {
        val result = text.substring(0, changeIndex)
        binding.etPreferVisitDate.setText(result)
        binding.etPreferVisitDate.setSelection(changeIndex)
    }

    private fun setMaxLength() {
        binding.etPreferVisitDate.filters =
            arrayOf(InputFilter.LengthFilter(DATE_MAX_FORMAT_LENGTH))
    }

    private fun observe() {
        viewModel.repairBasicRecord.observe(viewLifecycleOwner) {
            if (it.status == 201) {
                val action = HomeRepairApplyFragmentDirections.actionHomeRepairApplyToHome()
                findNavController().navigate(action)
            }
        }
    }
}