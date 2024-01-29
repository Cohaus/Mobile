package com.solution.gdsc.ui.login

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityLoginBinding
import com.solution.gdsc.ui.MainActivity
import com.solution.gdsc.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    private var validId = ""
    private var validPassword = ""
    private var isValidToken = false

    override fun setLayout() {
        viewModel.autoLogin()
        setInputText()
        setLoginClick()
        setSignUpButtonClick()
        observe()
    }

    private fun setSignUpButtonClick() {
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setInputText() {
        with(binding) {
            etInputLoginId.doAfterTextChanged {
                val id = it?.toString()
                if (id.isNullOrBlank()) return@doAfterTextChanged
                validId = id
            }
            etInputLoginPassword.doAfterTextChanged {
                val password = it?.toString()
                if (password.isNullOrBlank()) return@doAfterTextChanged
                validPassword = password
            }
        }
    }

    private fun setLoginClick() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(validId, validPassword)
        }
    }

    private fun observe() {
        viewModel.userInfo.observe(this) {
            isValidToken = it.accessToken.isNotEmpty()
            if (isValidToken) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        viewModel.isValidLogin.observe(this) {
            isValidToken = it
            if (isValidToken) {
                checkUserLogin()
            }
        }
    }

    private fun checkUserLogin() {
        if (isValidToken) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}