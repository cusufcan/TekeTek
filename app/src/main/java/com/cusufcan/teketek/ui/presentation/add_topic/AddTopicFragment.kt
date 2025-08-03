package com.cusufcan.teketek.ui.presentation.add_topic

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.cusufcan.teketek.databinding.FragmentAddTopicBinding
import com.cusufcan.teketek.domain.model.Topic
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTopicFragment : BottomSheetDialogFragment(), TextWatcher {
    private var _binding: FragmentAddTopicBinding? = null
    private val binding get() = _binding!!

    private lateinit var topicNameInput: EditText
    private lateinit var closeButton: ImageView
    private lateinit var addButton: Button

    private var topicName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindEvents()
    }

    private fun bindViews() {
        topicNameInput = binding.topicNameInput
        closeButton = binding.closeButton
        addButton = binding.addButton
    }

    private fun bindEvents() {
        closeButton.setOnClickListener {
            dismiss()
        }

        addButton.setOnClickListener {
            dismiss()

            topicName?.let {
                val action = AddTopicFragmentDirections.actionAddTopicFragmentToDebateFragment(
                    topic = Topic(title = it),
                )
                findNavController().navigate(action)
            }
        }

        topicNameInput.addTextChangedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int,
    ) {
        topicName = topicNameInput.text.toString().trim()
        val isNotEmpty = topicName?.trim()?.isNotEmpty()
        addButton.isEnabled = isNotEmpty ?: false
    }
}