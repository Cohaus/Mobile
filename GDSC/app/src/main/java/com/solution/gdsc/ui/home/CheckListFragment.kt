package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentChecklistBinding

class CheckListFragment : BaseFragment<FragmentChecklistBinding>(R.layout.fragment_checklist) {
    override fun setLayout() {
        binding.toolbarChecklist.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}