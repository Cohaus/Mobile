package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeAiCategoryBinding
import com.solution.gdsc.ui.common.AiCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAiCategoryFragment : BaseFragment<FragmentHomeAiCategoryBinding>(R.layout.fragment_home_ai_category) {
    private var isRebarSelect = false
    private var isPeelingSelect = false
    private var isCrackSelect = false
    private var category = ""

    override fun setLayout() {
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnCategoryRebar.setOnClickListener {
                isRebarSelect = if (!isRebarSelect) {
                    btnCategoryRebar.setImageResource(R.mipmap.image_rebar_select)
                    btnCategoryPeeling.setImageResource(R.mipmap.image_peeling)
                    btnCategoryCrack.setImageResource(R.mipmap.image_crack)
                    isPeelingSelect = false
                    isCrackSelect = false
                    category = AiCategory.EXPOSED.name
                    true
                } else {
                    btnCategoryRebar.setImageResource(R.mipmap.image_rebar)
                    false
                }
            }
            btnCategoryPeeling.setOnClickListener {
                isPeelingSelect = if (!isPeelingSelect) {
                    btnCategoryPeeling.setImageResource(R.mipmap.image_peeling_select)
                    btnCategoryRebar.setImageResource(R.mipmap.image_rebar)
                    btnCategoryCrack.setImageResource(R.mipmap.image_crack)
                    isRebarSelect = false
                    isCrackSelect = false
                    category = AiCategory.PEELING.name
                    true
                } else {
                    btnCategoryPeeling.setImageResource(R.mipmap.image_peeling)
                    false
                }

            }
            btnCategoryCrack.setOnClickListener {
                isCrackSelect = if (!isCrackSelect) {
                    btnCategoryPeeling.setImageResource(R.mipmap.image_peeling)
                    btnCategoryRebar.setImageResource(R.mipmap.image_rebar)
                    btnCategoryCrack.setImageResource(R.mipmap.image_crack_select)
                    isRebarSelect = false
                    isPeelingSelect = false
                    category = AiCategory.CRACK.name
                    true
                } else {
                    btnCategoryCrack.setImageResource(R.mipmap.image_crack)
                    false
                }
            }
            btnCategoryNextButton.setOnClickListener {
                val action = HomeAiCategoryFragmentDirections.actionHomeAiCategoryToCamera(category)
                findNavController().navigate(action)
            }
            toolbarAiCategory.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}