package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun setLayout() {
        with(binding) {
            btnAiDiagnosis.setOnClickListener {
                //dispatchTakePictureIntent()
                val action = HomeFragmentDirections.actionHomeToCamera()
                findNavController().navigate(action)
            }
            btnLoginView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToLogin()
                findNavController().navigate(action)
            }
        }
    }
}