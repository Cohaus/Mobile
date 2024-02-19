package com.solution.gdsc.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solution.gdsc.databinding.FragmentHomeUseGuidItemBinding
import com.solution.gdsc.ui.common.UseGuide
import com.solution.gdsc.util.Utils

class HomeUseGuidItemFragment : Fragment() {
    private var _binding: FragmentHomeUseGuidItemBinding? = null
    private lateinit var useGuide: UseGuide

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUseGuidItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    private fun setLayout() {
        useGuide = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(Utils.KEY_USE_GUIDE, UseGuide::class.java)
        } else {
            arguments?.getSerializable(Utils.KEY_USE_GUIDE) as? UseGuide
        } ?: UseGuide.ONE
        binding.ivUseGuideItem.setImageResource(useGuide.resId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(useGuide: UseGuide): HomeUseGuidItemFragment {
            return HomeUseGuidItemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Utils.KEY_USE_GUIDE, useGuide)
                }
            }
        }
    }
}