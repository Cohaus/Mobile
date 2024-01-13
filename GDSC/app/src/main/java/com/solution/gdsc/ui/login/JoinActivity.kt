package com.solution.gdsc.ui.login

import com.solution.gdsc.R
import com.solution.gdsc.base.BaseActivity
import com.solution.gdsc.databinding.FragmentJoinBinding

class JoinActivity : BaseActivity<FragmentJoinBinding>(R.layout.fragment_join) {
    override fun setLayout() {
        binding.toolbarJoinMembership.setNavigationOnClickListener {
            finish()
        }
    }
}