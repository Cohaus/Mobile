package com.solution.gdsc.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentDialogMapRepairApplyBinding
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import com.solution.gdsc.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogMapRepairApplyFragment : DialogFragment() {
    private var _binding: FragmentDialogMapRepairApplyBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DialogMapRepairApplyFragmentArgs>()
    private val viewModel by viewModels<MapViewModel>()

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
        pop()
        getCurrentDate()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.AppDialog)
    }

    private fun setLayout() {
        with(binding) {
            val title = tvRepairApplyMessageBold.text
            tvRepairApplyMessageBold.text = title.toString().format(args.title)
            btnMapApplyCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            btnMapVolunteerApply.setOnClickListener {
                val date = getCurrentDate()
                viewModel.patchRepairInfo(date, args.repairId)
            }
        }
    }

    private fun getCurrentDate(): String {
        val date = DateFormatter.getCurrentTime()
        val newDate = DateFormatter.convertToDate(date)
        return DateFormatter.convertToRepairDateFormat(newDate!!)
    }

    private fun pop() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.patchSuccess.collectLatest {
                    if (it.status in 200..299) {
                        val action = DialogMapRepairApplyFragmentDirections.actionMapRepairApplyToMap()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}