package com.solution.gdsc.ui.login

import androidx.core.widget.doAfterTextChanged
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityJoinBinding

class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {
    private var userId = ""
    private var password = ""
    private var userName = ""
    private var userPhoneNumber = ""

    private var isValidUserId = false
    private var isValidPassword = false
    private var isValidUserName = false
    private var isValidUserPhoneNumber = false

    override fun setLayout() {
        binding.toolbarJoinMembership.setNavigationOnClickListener {
            finish()
        }
        setTextInput()
    }

    private fun setTextInput() {
        with(binding) {
            etInputJoinId.doAfterTextChanged {
                userId = it?.toString() ?: ""
                isValidUserId = isValidInput(userId)
                updateButtonEnableState()

            }
            etInputJoinPassword.doAfterTextChanged {
                password = it?.toString() ?: ""
                isValidPassword = isValidInput(password)
                updateButtonEnableState()
            }
            etInputJoinName.doAfterTextChanged {
                userName = it?.toString() ?: ""
                isValidUserName = isValidInput(userName)
                updateButtonEnableState()
            }
            etInputJoinPhoneNumber.doAfterTextChanged {
                userPhoneNumber = it?.toString() ?: ""
                isValidUserPhoneNumber = isValidInput(userPhoneNumber)
                updateButtonEnableState()
            }
        }
    }

    private fun isValidInput(text: String): Boolean = text.isNotBlank()

    private fun updateButtonEnableState() {
        binding.btnJoinConfirm.isEnabled =
            isValidUserId && isValidPassword && isValidUserName && isValidUserPhoneNumber
    }
}