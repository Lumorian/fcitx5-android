package org.fcitx.fcitx5.android.input.status

import androidx.annotation.DrawableRes
import org.fcitx.fcitx5.android.R
import org.fcitx.fcitx5.android.core.Action

sealed class StatusAreaEntry(
    val label: String,
    @DrawableRes
    val icon: Int,
    val active: Boolean
) {
    class Android(label: String, icon: Int, val type: Type) :
        StatusAreaEntry(label, icon, false) {
        enum class Type {
            InputMethod,
            ReloadConfig,
            Keyboard,
            ThemeList
        }
    }

    class Fcitx(val action: Action, label: String, icon: Int, active: Boolean) :
        StatusAreaEntry(label, icon, active)

    companion object {
        private fun Action.isActive() = icon.endsWith("-active") || isChecked

        private fun drawableRes(icon: String, active: Boolean = false) = when (icon) {
            "fcitx-chttrans-active" -> R.drawable.ic_fcitx_status_chttrans_trad
            "fcitx-chttrans-inactive" -> R.drawable.ic_fcitx_status_chttrans_simp
            "fcitx-punc-active" -> R.drawable.ic_fcitx_status_punc_active
            "fcitx-punc-inactive" -> R.drawable.ic_fcitx_status_punc_inactive
            "fcitx-fullwidth-active" -> R.drawable.ic_fcitx_status_fullwidth_active
            "fcitx-fullwidth-inactive" -> R.drawable.ic_fcitx_status_fullwidth_inactive
            "fcitx-remind-active" -> R.drawable.ic_fcitx_status_prediction_active
            "fcitx-remind-inactive" -> R.drawable.ic_fcitx_status_prediction_inactive
            else -> if (active) R.drawable.ic_baseline_code_24 else R.drawable.ic_baseline_code_off_24
        }

        fun fromAction(it: Action): Fcitx {
            val active = it.isActive()
            return Fcitx(it, it.shortText, drawableRes(it.icon, active), active)
        }
    }
}