package com.cusufcan.teketek.data.remote

import com.cusufcan.teketek.domain.model.Topic
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TOPICS_KEY = "topics"

class TopicService @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    suspend fun getTopics(): List<Topic> = withContext(Dispatchers.IO) {
        val snapshot = firestore.collection(TOPICS_KEY).get().await()
        snapshot.documents.mapNotNull { it.toObject(Topic::class.java) }
    }
}