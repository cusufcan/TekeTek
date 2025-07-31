package com.cusufcan.teketek.ui.presentation.alert

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.cusufcan.teketek.R

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.end_debate_title).setMessage(R.string.end_debate_desc)
                .setPositiveButton(R.string.yes) { dialog, id ->
                    navigateToHome()
                }.setNegativeButton(R.string.no) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun navigateToHome() {
        val action = AlertDialogFragmentDirections.actionAlertDialogFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}