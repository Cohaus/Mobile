package com.solution.gdsc.ui.profile.setting

import android.content.res.ColorStateList
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentSettingVolunteerRegistrationBinding
import com.solution.gdsc.ui.common.VolunteerType
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingVolunteerRegistrationFragment
    : BaseFragment<FragmentSettingVolunteerRegistrationBinding>(
    R.layout.fragment_setting_volunteer_registration
) {
    val viewModel by viewModels<ProfileViewModel>()

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
            btnRegistrationVolunteer.setOnClickListener {
                val type = getUserType()
                putVolunteer(type)
                observe()
            }
            toolbarVolunteerRegistration.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun getUserType() = if (isSingle == true) VolunteerType.SINGLE.type else VolunteerType.ORGANIZATION.type

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

    private fun putVolunteer(type: String) {
        var name: String? = null
        if (validOrganizationName != "") {
            name = validOrganizationName
        }
        viewModel.putVolunteerUser(type, name)
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hasResult.collectLatest {
                    if (it.status in 200..299) {
                        val action = SettingVolunteerRegistrationFragmentDirections
                            .actionSettingVolunteerRegistrationToVolunteerReregistrationDialog()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}