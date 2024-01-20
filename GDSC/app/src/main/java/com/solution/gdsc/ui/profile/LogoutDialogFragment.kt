package com.solution.gdsc.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.databinding.DialogLogoutBinding

class LogoutDialogFragment : DialogFragment() {
    private var _binding: DialogLogoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLogoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLogoutCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            btnLogoutConfirm.setOnClickListener {
                // Logout 기능 구현
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.AppDialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}