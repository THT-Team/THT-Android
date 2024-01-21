package com.tht.tht.data.local.dao.topic

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyTopicDaoImpl @Inject constructor(
    private val sp: SharedPreferences,
    private val gson: Gson
) : DailyTopicDao {
    override fun fetchDailyTopics(): DailyTopicResponse {
        return gson.fromJson(
            sp.getString(DAILY_TOPIC_STATE_KEY, "").orEmpty(),
            DailyTopicResponse::class.java
        )
    }

    override fun saveDailyTopic(topic: DailyTopicResponse) {
        sp.edit {
            putString(DAILY_TOPIC_STATE_KEY, gson.toJson(topic))
        }
    }

    override fun clear() {
        sp.edit { remove(DAILY_TOPIC_STATE_KEY) }
    }

    companion object {
        private const val DAILY_TOPIC_STATE_KEY = "daily_topic_state_key"
    }
}
