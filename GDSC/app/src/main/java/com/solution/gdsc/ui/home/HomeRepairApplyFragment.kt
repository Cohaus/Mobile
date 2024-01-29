package com.solution.gdsc.ui.home

import android.text.InputFilter
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRepairApplyBinding

private const val DATE_FORMAT_DELIMITER = " / "
private const val DATE_FORMAT_DELETE_DELIMITER = " /"
private const val DATE_MAX_FORMAT_LENGTH = 14

class HomeRepairApplyFragment : BaseFragment<FragmentHomeRepairApplyBinding>(R.layout.fragment_home_repair_apply) {

    private var validDate = ""
    private var validTitle = ""
    private var validContent = ""

    private var isValidTitle = false
    private var isValidContent = false
    private var isValidDate = false

    override fun setLayout() {
        binding.btnRepairApplySave.setOnClickListener {
            val action = HomeRepairApplyFragmentDirections.actionHomeRepairApplyToHome()
            findNavController().navigate(action)
        }
        binding.toolbarRepairApply.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setTextInput()
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
                    isValidDelete(visitDate, 4) -> {
                        applyDeleteFormat(visitDate, 1)
                    }
                    visitDate.length == 2 && !visitDate.contains(DATE_FORMAT_DELIMITER) -> {
                        setMonthMaxInput()
                        applyDateFormat(visitDate, 2, 5)
                    }
                    isValidDelete(visitDate, 9) -> {
                        applyDeleteFormat(visitDate, 6)
                    }
                    visitDate.length == 7 -> {
                        setDayMaxInput()
                        applyDateFormat(visitDate, 7, 10)
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
                if (visitDate.length < 2 || visitDate.length >= 3) return@doAfterTextChanged
                val month = visitDate.substring(0, 2).toInt()
                val etc = visitDate.substring(2)
                if (month <= 0 || month > 12 ) {
                    val result = "12$etc"
                    etPreferVisitDate.setText(result)
                    etPreferVisitDate.setSelection(5)
                }
            }
        }
    }

    private fun setDayMaxInput() {
        with(binding) {
            etPreferVisitDate.doAfterTextChanged {
                val visitDate = it?.toString()
                if (visitDate.isNullOrBlank()) return@doAfterTextChanged
                if (visitDate.length < 7 || visitDate.split(" / ").size != 2 || visitDate.length >= 8) return@doAfterTextChanged
                val split = visitDate.split(" / ")
                val day = split[1].toInt()
                val month = split[0]
                if (day <= 0 || day > 31 ) {
                    val result = "$month / ${31}"
                    etPreferVisitDate.setText(result)
                    etPreferVisitDate.setSelection(10)
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
}