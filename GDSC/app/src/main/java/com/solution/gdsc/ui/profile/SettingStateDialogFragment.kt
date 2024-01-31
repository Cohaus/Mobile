package com.solution.gdsc.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.databinding.FragmentDialogSettingStateBinding
import com.solution.gdsc.ui.common.DialogCategory
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingStateDialogFragment : DialogFragment() {
    private var _binding: FragmentDialogSettingStateBinding? = null
    private val binding get() = _binding!!
    private val args: SettingStateDialogFragmentArgs by navArgs()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSettingStateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.dialogCategory) {
            DialogCategory.LOGOUT -> setCategoryLogout()
            else -> setCategoryWithdrawal()
        }
        observe()
    }

    private fun setCategoryLogout() {
        with(binding) {
            groupLogout.visibility = ViewGroup.VISIBLE
            btnLogoutCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            btnLogoutConfirm.setOnClickListener {
                viewModel.logout()
            }
        }
    }

    private fun setCategoryWithdrawal() {
        with(binding) {
            groupWithdrawal.visibility = ViewGroup.VISIBLE
            btnWithdrawalCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            btnLogoutConfirm.setOnClickListener {
                // 회원 탈퇴 기능 구가
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

    private fun observe() {
        viewModel.isLogout.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}