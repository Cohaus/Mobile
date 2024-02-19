package com.solution.gdsc.ui.login

import android.content.Intent
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.ActivityJoinCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.format

@AndroidEntryPoint
class JoinCompleteActivity : BaseActivity<ActivityJoinCompleteBinding>(R.layout.activity_join_complete) {

    override fun setLayout() {
        binding.tvJoinCompleteMessage.text = getString(
            R.string.text_join_complete_message,
            format(intent.getStringExtra("name") ?: "error")
        )
        clickListener()
    }

    private fun clickListener() {
        binding.toolbarJoinComplete.setNavigationOnClickListener {
            finish()
        }
        binding.btnStartService.setOnClickListener {
            startService()
        }
    }

    private fun startService() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}