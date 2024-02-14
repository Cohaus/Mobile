package com.solution.gdsc.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentDialogEcoWasteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogEcoWasteFragment : DialogFragment() {
    private var _binding: FragmentDialogEcoWasteBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DialogEcoWasteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogEcoWasteBinding.inflate(layoutInflater, container, false)
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
        with(binding) {
            btnMapWasteEcoCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            btnMapWasteEcoFind.setOnClickListener {
                val action = DialogEcoWasteFragmentDirections.actionDialogEcoWasteToDetailFindWasteFacility(args.repairId)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}