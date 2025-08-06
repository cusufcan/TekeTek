package com.cusufcan.teketek.ui.presentation.summary

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cusufcan.teketek.R
import com.cusufcan.teketek.databinding.FragmentSummaryBinding
import com.cusufcan.teketek.ui.adapter.summary.SummaryAdapter
import com.cusufcan.teketek.util.AppLogger
import kotlinx.coroutines.Runnable
import kotlin.math.abs

class SummaryFragment : Fragment() {
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var topicText: TextView
    private lateinit var summaryText: TextView
    private lateinit var strengthsViewPager: ViewPager2
    private lateinit var weaknessesViewPager: ViewPager2

    private val args: SummaryFragmentArgs by navArgs()

    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 3000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindValues()
        bindBackPressed()
    }

    private fun bindViews() {
        topicText = binding.topicText
        summaryText = binding.summaryText
        strengthsViewPager = binding.strengthsViewPager
        weaknessesViewPager = binding.weaknessesViewPager
    }

    private fun bindValues() {
        topicText.text = args.topic.title
        summaryText.text = args.summary.summary
        setupAutoSlider(strengthsViewPager, args.summary.strengths ?: emptyList(), true)
        setupAutoSlider(weaknessesViewPager, args.summary.weaknesses ?: emptyList(), false)
    }

    private fun bindBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val title = getString(R.string.quit_app_title)
            val desc = getString(R.string.quit_app_desc)
            val action = SummaryFragmentDirections.actionSummaryFragmentToAlertDialogFragment(
                title,
                desc,
            )
            findNavController().navigate(action)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupAutoSlider(viewPager: ViewPager2, items: List<String>, isStrengths: Boolean) {
        AppLogger.d("setupAutoSlider: $items")
        if (items.isEmpty()) return

        viewPager.adapter = SummaryAdapter(items, requireContext(), isStrengths)

        var currentPage = Int.MAX_VALUE / 2
        viewPager.setCurrentItem(currentPage, false)

        viewPager.setPageTransformer { page, position ->
            val absPos = abs(position)
            page.scaleY = 0.85f + (1 - absPos) * 0.15f
            page.alpha = 0.5f + (1 - absPos) * 0.5f
        }

        viewPager.offscreenPageLimit = 3
        val recyclerView = viewPager.getChildAt(0) as RecyclerView
        recyclerView.setPadding(60, 0, 60, 0)
        recyclerView.clipToPadding = false

        val runnable = object : Runnable {
            override fun run() {
                currentPage++
                viewPager.setCurrentItem(currentPage, true)
                handler.postDelayed(this, delay)
            }
        }

        recyclerView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handler.removeCallbacks(runnable)
                }

                MotionEvent.ACTION_UP -> {
                    handler.postDelayed(runnable, delay)
                    v.performClick()
                }
            }
            false
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })

        handler.postDelayed(runnable, delay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)
    }
}