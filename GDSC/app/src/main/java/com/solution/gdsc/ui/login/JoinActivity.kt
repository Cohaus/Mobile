package com.solution.gdsc.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityJoinBinding
import com.solution.gdsc.ui.extensions.changeNextVisibleWithFocus
import com.solution.gdsc.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {

    private val viewModel by viewModels<LoginViewModel>()

    private var userId = ""
    private var password = ""
    private var userName = ""
    private var userPhoneNumber = ""
    private var userEmail = ""

    private var isValidUserId = false
    private var isValidPassword = false
    private var isValidUserName = false
    private var isValidUserPhoneNumber = false
    private var isValidUserEmail = false

    override fun setLayout() {
        binding.toolbarJoinMembership.setNavigationOnClickListener {
            finish()
        }
        binding.btnJoinConfirm.setOnClickListener {
            signUp()
            sign()
        }
        setTextInput()
        setEditTextVisible()
    }

    private fun setTextInput() {
        with(binding) {
            etInputJoinId.doAfterTextChanged {
                userId = it?.toString() ?: ""
                isValidUserId = isValidIdInput(userId)
                updateButtonEnableState()

            }
            etInputJoinPassword.doAfterTextChanged {
                password = it?.toString() ?: ""
                isValidPassword = isValidPasswordInput(password)
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
            etInputJoinEmail.doAfterTextChanged {
                userEmail = it?.toString() ?: ""
                isValidUserEmail = isValidInput(userEmail)
                updateButtonEnableState()
            }
        }
    }

    private fun setEditTextVisible() {
        with(binding) {
            etInputJoinId.changeNextVisibleWithFocus(etInputJoinPassword)
            etInputJoinPassword.changeNextVisibleWithFocus(etInputJoinName)
            etInputJoinName.changeNextVisibleWithFocus(etInputJoinPhoneNumber)
            etInputJoinPhoneNumber.changeNextVisibleWithFocus(etInputJoinEmail)
        }
    }

    private fun signUp() {
        viewModel.signUp(userId, password, userName, userPhoneNumber, userEmail)
    }

    private fun isValidInput(text: String): Boolean = text.isNotBlank()

    private fun isValidIdInput(text: String): Boolean = text.length >= 4

    private fun isValidPasswordInput(text: String): Boolean = text.length >= 4

    private fun updateButtonEnableState() {
        binding.btnJoinConfirm.isEnabled =
            isValidUserId && isValidPassword && isValidUserName && isValidUserPhoneNumber && isValidUserEmail
    }

    private fun sign() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUpResult.collectLatest {
                    next(it.status, it.message)
                }
            }
        }
    }

    private fun next(status: Int, message: String) {
        if (status in 200..299) {
            val intent = Intent(this, JoinCompleteActivity::class.java).apply {
                putExtra("name", userName)
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}