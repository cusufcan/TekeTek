package com.cusufcan.teketek.ui.presentation.alert

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cusufcan.teketek.R

class AlertDialogFragment : DialogFragment() {
    private val args: AlertDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = args.title
        val desc = args.description

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title).setMessage(desc).setPositiveButton(R.string.yes) { dialog, id ->
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