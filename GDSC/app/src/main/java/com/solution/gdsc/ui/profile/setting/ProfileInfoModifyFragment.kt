package com.solution.gdsc.ui.profile.setting

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileInfoModifyBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileInfoModifyFragment : BaseFragment<FragmentProfileInfoModifyBinding>(R.layout.fragment_profile_info_modify) {
    private val args by navArgs<ProfileInfoModifyFragmentArgs>()
    private val viewModel by viewModels<ProfileViewModel>()

    private var validId: String = ""
    private var validName: String = ""
    private var validTel: String = ""
    private var validEmail: String = ""

    private var isValidId = false
    private var isValidName = false
    private var isValidTel = false
    private var isValidEmail = false


    override fun setLayout() {
        val userInfo = args.userInfo
        binding.userInfo = userInfo
        binding.toolbarInfoModify.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setInputText()
        clickUpdateButton()
        observe()
    }

    private fun clickUpdateButton() {
        binding.btnModifyConfirm.setOnClickListener {
            updateInfo()
        }
    }

    private fun updateInfo() {
        viewModel.updateUserInfo(validId, validName, validTel, validEmail)
    }

    private fun setInputText() {
        with(binding) {
            etInputModifyId.doAfterTextChanged {
                validId = it?.toString() ?: ""
                isValidId = validInput(validId)
                updateButtonState()
            }
            etInputModifyName.doAfterTextChanged {
                validName = it?.toString() ?: ""
                isValidName = validInput(validName)
                updateButtonState()
            }
            etInputModifyPhoneNumber.doAfterTextChanged {
                validTel = it?.toString() ?: ""
                isValidTel = validInput(validTel)
                updateButtonState()
            }
            etInputModifyEmail.doAfterTextChanged {
                validEmail = it?.toString() ?: ""
                isValidEmail = validInput(validEmail)
                updateButtonState()
            }
        }
    }

    private fun validInput(text: String) = text.length > 4

    private fun updateButtonState() {
        binding.btnModifyConfirm.isEnabled = isValidId && isValidName && isValidEmail && isValidTel
    }

    private fun observe() {
        viewModel.isUpdate.observe(viewLifecycleOwner) {
            if (it.status == 200) {
                findNavController().navigateUp()
            }
        }
    }
}