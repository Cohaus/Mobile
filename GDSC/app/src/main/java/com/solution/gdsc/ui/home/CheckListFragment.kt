package com.solution.gdsc.ui.home

import android.widget.CheckBox
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentChecklistBinding
import com.solution.gdsc.ui.extensions.changeTextColor

class CheckListFragment : BaseFragment<FragmentChecklistBinding>(R.layout.fragment_checklist) {
    override fun setLayout() {
        with(binding) {
            cbCheckboxFirst.changeTextColor()
            cbCheckboxSecond.changeTextColor()
            cbCheckboxThird.changeTextColor()
            cbCheckboxFourth.changeTextColor()
            cbCheckboxFifth.changeTextColor()

            toolbarChecklist.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}