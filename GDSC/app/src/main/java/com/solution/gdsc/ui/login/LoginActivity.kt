package com.solution.gdsc.ui.login

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityLoginBinding
import com.solution.gdsc.ui.MainActivity
import com.solution.gdsc.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    private var validId = ""
    private var validPassword = ""

    override fun setLayout() {
        viewModel.checkToken()
        binding.isLoading = true
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (ChallengeApplication.getInstance().tokenManager.accessTokenFlow.first() != null) {
                    autoLogin()
                    finish()
                } else {
                    binding.isLoading = false
                }
            }
        }
        viewModel.userInfo.observe(this) {
            if (it.accessToken.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun autoLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}