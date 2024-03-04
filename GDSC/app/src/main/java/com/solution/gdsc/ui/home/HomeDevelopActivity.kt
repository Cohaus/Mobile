package com.solution.gdsc.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solution.gdsc.databinding.ActivityHomeDevelopBinding

class HomeDevelopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDevelopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDevelopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackButton()
    }

    private fun setBackButton() {
        binding.toolbarHomeDevelop.setNavigationOnClickListener {
            finish()
        }
    }
}