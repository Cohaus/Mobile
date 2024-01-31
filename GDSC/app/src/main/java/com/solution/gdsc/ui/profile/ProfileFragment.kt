package com.solution.gdsc.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileBinding
import com.solution.gdsc.domain.model.RecordSaveDetail
import com.solution.gdsc.ui.profile.adapter.PostClickListener
import com.solution.gdsc.ui.profile.adapter.RecordSaveApter
import com.solution.gdsc.ui.profile.adapter.RepairApplyAdapter
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile), PostClickListener {
    private val viewModel by viewModels<ProfileViewModel>()

    override fun setLayout() {
        viewModel.getUserInfo()
        val adapter = RepairApplyAdapter(this)
        val saveApter = RecordSaveApter(this)
        addData(adapter)
        addRecordSaveData(saveApter)
        setToolbarMenu()
        observe()
    }

    private fun setToolbarMenu() {
        binding.toolbarProfile.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_ic_setting -> {
                    val action = ProfileFragmentDirections.actionProfileToProfileSetting()
                    findNavController().navigate(action)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun observe() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.userDto = it
        }
    }

    private fun addData(adapter: RepairApplyAdapter) {
        val items = mutableListOf<RecordSaveDetail>()
        items.add(
            RecordSaveDetail("집 도배 사진", "a", "무언가 잘못",
                "송파구", "2023.01.02", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("누수", "a", "무언가 잘못",
                "분당구", "2023.01.04", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("콘크리트 균열", "a", "무언가 잘못",
                "서울시 마포구", "2023.01.08", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("콘크리트 파손", "a", "무언가 잘못",
                "서울시 강남구", "2023.01.02", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("집 추락", "a", "무언가 잘못",
                "창원시 성산구", "2023.01.08", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("집 파괴", "a", "무언가 잘못",
                "서울시 성산구", "2023.01.09", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 서초구", "a", "무언가 잘못",
                "성남시 수정구", "2023.01.010", "B 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 마포구", "a", "무언가 잘못",
                "성남시 수정구", "2023.01.12", "C 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 강남구", "a", "무언가 잘못",
                "성남시 분당구", "2023.01.13", "D 공사현장")
        )
        adapter.add(items)
        binding.rvRepairApplyList.adapter = adapter
    }

    private fun addRecordSaveData(adapter: RecordSaveApter) {
        val items = mutableListOf<RecordSaveDetail>()
        items.add(
            RecordSaveDetail("집 도배 사진", "a", "무언가 잘못",
                "송파구", "2023.01.02", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("누수", "a", "무언가 잘못",
                "분당구", "2023.01.04", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("콘크리트 균열", "a", "무언가 잘못",
                "서울시 마포구", "2023.01.08", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("콘크리트 파손", "a", "무언가 잘못",
                "서울시 강남구", "2023.01.02", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("집 추락", "a", "무언가 잘못",
                "창원시 성산구", "2023.01.08", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("집 파괴", "a", "무언가 잘못",
                "서울시 성산구", "2023.01.09", "A 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 서초구", "a", "무언가 잘못",
                "성남시 수정구", "2023.01.010", "B 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 마포구", "a", "무언가 잘못",
                "성남시 수정구", "2023.01.12", "C 공사현장")
        )
        items.add(
            RecordSaveDetail("서울시 강남구", "a", "무언가 잘못",
                "성남시 분당구", "2023.01.13", "D 공사현장")
        )
        adapter.add(items)
        binding.rvSaveList.adapter = adapter
    }

    override fun onPostClick(post: RecordSaveDetail) {
        val action = ProfileFragmentDirections.actionProfileToPostDetail(post)
        findNavController().navigate(action)
    }
}