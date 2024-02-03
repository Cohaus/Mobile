package com.solution.gdsc.ui.profile.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentVolunteerRegistrationDialogBinding
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerReregistrationDialogFragment : DialogFragment() {
    private var _binding: FragmentVolunteerRegistrationDialogBinding? = null
    private val binding get() = _binding!!
    private val args: VolunteerReregistrationDialogFragmentArgs by navArgs()
    val viewModel by viewModels<ProfileViewModel>()

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
        observe()
    }

    fun setLayout() {
        with(binding) {
            btnVolunteerRegistrationConfirm.setOnClickListener {
                putVolunteer()
            }
        }
    }

    private fun putVolunteer() {
        var name: String? = null
        if (args.organizationName != "") {
            name = args.organizationName
        }
        Log.e("volunteer regi", "${args.type} $name")
        viewModel.putVolunteerUser(args.type, name)
    }

    private fun observe() {
        viewModel.hasResult.observe(viewLifecycleOwner) {
            Log.e("put result: ", it.toString())
            val action = VolunteerReregistrationDialogFragmentDirections
                .actionVolunteerReregistrationDialogToProfile()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}