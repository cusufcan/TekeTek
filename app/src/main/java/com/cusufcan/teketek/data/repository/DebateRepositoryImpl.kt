package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class DebateRepositoryImpl @Inject constructor() : DebateRepository {
    override suspend fun getAvailableTopics(): List<Topic> {
        return listOf(
            Topic("1", "Yapay zekâ tehlikeli mi?", "Yapay zekânın insanlık için riskleri var mı?"),
            Topic("2", "Uzaktan eğitim kalıcı olmalı mı?", "Pandemi sonrası eğitimde kalıcılığı"),
            Topic("3", "Sosyal medya yasaklanmalı mı?", "Toplum üzerindeki etkileri")
        )
    }
}