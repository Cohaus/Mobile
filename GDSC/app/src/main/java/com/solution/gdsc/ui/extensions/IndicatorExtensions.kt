package com.solution.gdsc.ui.extensions

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.solution.gdsc.ui.common.RepairStatus

@BindingAdapter("setProgressByStatus")
fun CircularProgressIndicator.setProgressByStatus(status: String?) {
    if (!status.isNullOrEmpty()) {
        when (status) {
            RepairStatus.REQUEST.type -> progress = 33
            RepairStatus.PROCEED.type -> progress = 77
            RepairStatus.COMPLETE.type -> progress = 100
        }
    }
}