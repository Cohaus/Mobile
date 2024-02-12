package com.solution.gdsc.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentDialogMapRepairApplyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogMapRepairApplyFragment : DialogFragment() {
    private var _binding: FragmentDialogMapRepairApplyBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DialogMapRepairApplyFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogMapRepairApplyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.AppDialog)
    }

    private fun setLayout() {
        val title = binding.tvRepairApplyMessageBold.text
        val name = binding.tvRepairApplyMessageMedium.text
        binding.tvRepairApplyMessageBold.text = title.toString().format(args.title)
        binding.tvRepairApplyMessageMedium.text = name.toString().format(args.repairInfo.userName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}