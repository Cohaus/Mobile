package com.solution.gdsc.ui.home

import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeAiCategoryBinding

class HomeAiCategoryFragment : BaseFragment<FragmentHomeAiCategoryBinding>(R.layout.fragment_home_ai_category) {

    override fun setLayout() {
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnCategoryRebar.setOnClickListener {

            }
            btnCategoryPeeling.setOnClickListener {

            }
            btnCategoryRebar.setOnClickListener {

            }
            btnCategoryNextButton.setOnClickListener {

            }
        }
    }
}