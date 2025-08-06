package com.cusufcan.teketek.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Summary(
    private val debateId: String? = null,
    val summary: String? = null,
    val strengths: List<String>? = null,
    val weaknesses: List<String>? = null,
    val message: String? = null,
) : Parcelable