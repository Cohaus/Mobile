package com.solution.gdsc.ui.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentVolunteerRegistrationDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerReregistrationDialogFragment : DialogFragment() {
    private var _binding: FragmentVolunteerRegistrationDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolunteerRegistrationDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.AppDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    fun setLayout() {
        with(binding) {
            btnVolunteerRegistrationConfirm.setOnClickListener {
                val action = VolunteerReregistrationDialogFragmentDirections
                    .actionVolunteerReregistrationDialogToProfile()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}