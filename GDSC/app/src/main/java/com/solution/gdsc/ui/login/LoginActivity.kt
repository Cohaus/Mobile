package com.solution.gdsc.ui.login

import android.content.Intent
import androidx.activity.viewModels
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityLoginBinding
import com.solution.gdsc.ui.MainActivity
import com.solution.gdsc.ui.login.viewmodel.LoginViewModel
import com.solution.gdsc.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun setLayout() {
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        observe()
        checkUserLogin()
    }

    private fun checkUserLogin() {
        if (tokenManager.checkUserToken()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observe() {
        viewModel.userInfo.observe(this) {
            // Token 처리
        }
    }
}