package com.solution.gdsc.ui.login

import android.content.Intent
import androidx.activity.viewModels
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityLoginBinding
import com.solution.gdsc.ui.MainActivity
import com.solution.gdsc.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun setLayout() {
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        observe()
        checkUserLogin()
    }

    private fun checkUserLogin() {
        if (ChallengeApplication.getInstance().tokenManager.checkUserToken()) {
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