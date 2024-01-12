package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.data.model.ConstPost
import com.solution.gdsc.data.model.ConstructionSiteCategory
import com.solution.gdsc.data.model.ConstructionSitePost
import com.solution.gdsc.databinding.FragmentProfileBinding
import com.solution.gdsc.ui.profile.adapter.ConstructionSiteListAdapter
import com.solution.gdsc.ui.profile.adapter.PostClickListener

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile), PostClickListener {
    override fun setLayout() {
        val adapter = ConstructionSiteListAdapter(this)
        addData(adapter)
        setToolbarMenu()
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

    private fun addData(adapter: ConstructionSiteListAdapter) {
        val items = mutableListOf<ConstPost>()
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("A 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "A 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("B 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "B 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("C 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "C 공사현장"))
        )
        items.add(ConstPost(
            ConstructionSiteCategory("D 공사현장"),
            ConstructionSitePost("서울시 서초구", "a", "무언가 잘못",
                "서울시 서초구", "2023.01.02", "D 공사현장"))
        )
        adapter.addPosts(items)
        binding.rvConstructionSitePostList.adapter = adapter
    }

    override fun onPostClick(category: String, post: ConstructionSitePost) {
        val action = ProfileFragmentDirections.actionProfileToPostDetail()
        findNavController().navigate(action)
    }
}