package com.solution.gdsc.ui.profile.setting

import android.content.res.ColorStateList
import androidx.core.widget.doAfterTextChanged
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentSettingVolunteerRegistrationBinding

class SettingVolunteerRegistrationFragment
    : BaseFragment<FragmentSettingVolunteerRegistrationBinding>(
    R.layout.fragment_setting_volunteer_registration
) {

    private var isSingle: Boolean? = null
    private var isSelect = false
    private var validOrganizationName = ""
    private var isValidOrganizationName = false

    override fun setLayout() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            cbVolunteerSingle.setOnClickListener {
                etInputOrganization.setText("")
                cbVolunteerOrganization.isChecked = false
                cbVolunteerSingle.isChecked = true
                isSingle = true
                isSelect = true
                updateOrganizationInputState()
                updateRegistrationButton()
            }
            cbVolunteerOrganization.setOnClickListener {
                cbVolunteerSingle.isChecked = false
                cbVolunteerOrganization.isChecked = true
                isSingle = false
                isSelect = true
                updateOrganizationInputState()
                updateRegistrationButton()
                setInputTextArea()
            }
        }
    }

    private fun updateOrganizationInputState() {
        if (isSingle == null) return
        with(binding) {
            if (isSingle!!) {
                tvInputOrganization.isEnabled = false
                etInputOrganization.isEnabled = false
                etInputOrganization.setBackgroundResource(R.drawable.shape_button_10dp_right_gray)
                tvOrganizationInputGuide.isEnabled = false
            } else {
                tvInputOrganization.isEnabled = true
                etInputOrganization.isEnabled = true
                etInputOrganization.background = null
                etInputOrganization.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_right));
                tvOrganizationInputGuide.isEnabled = true
            }
        }
    }

    private fun setInputTextArea() {
        with(binding) {
            etInputOrganization.doAfterTextChanged {
                validOrganizationName = it?.toString() ?: ""
                isValidOrganizationName = checkInput(validOrganizationName)
                updateRegistrationButton()
            }
        }
    }

    private fun checkInput(text: String): Boolean {
        return text.isNotEmpty()
    }

    private fun updateRegistrationButton() {
        with(binding) {
            if (isSingle == null) btnRegistrationVolunteer.isEnabled = false
            else if (isSingle == false && isSelect && isValidOrganizationName) {
                btnRegistrationVolunteer.isEnabled = true
            } else btnRegistrationVolunteer.isEnabled = isSingle == true && isSelect
        }
    }
}