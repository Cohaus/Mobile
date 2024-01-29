package com.solution.gdsc.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solution.gdsc.databinding.FragmentDetailMoreDialogBinding

class DetailMoreDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailMoreDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMoreDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout() {
        with(binding) {
            tvDetailRepairApplyInfo.setOnClickListener {
                val action =
                    DetailMoreDialogFragmentDirections.actionDetailMoreDialogToPostRepairApplyInfo()
                findNavController().navigate(action)
            }
        }
    }

}