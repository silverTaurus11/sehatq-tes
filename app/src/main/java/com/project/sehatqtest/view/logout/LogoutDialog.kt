package com.project.sehatqtest.view.logout

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.project.sehatqtest.R
import com.project.sehatqtest.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : DialogFragment() {

    companion object{
        const val TAG = "LogoutDialog"
    }

    private val logoutViewModel: LogoutViewModel by viewModels()

    var thirdPartyLogoutAction:() -> Unit = {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.logout_warning)
                    .setPositiveButton(R.string.ok_label) { _, _ ->
                        thirdPartyLogoutAction()
                        logoutViewModel.logout()
                        Handler(Looper.getMainLooper()).postDelayed({
                            it.startActivity(Intent(it, LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            })
                            it.finishAffinity()
                            dismissAllowingStateLoss()
                        }, 150)
                    }
                    .setNegativeButton(R.string.cancel_label) { _, _ ->
                        dismissAllowingStateLoss()
                    }
            builder.setCancelable(false).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
