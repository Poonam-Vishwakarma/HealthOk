package com.vcreate.healthok.data.repositories

import com.vcreate.healthok.data.remote.FirebaseClient


class VideoCallRepository {

    private val firebaseClient: FirebaseClient? = null

    private var instance: VideoCallRepository? = null
    fun getInstance(): VideoCallRepository? {
        if (instance == null) {
            instance = VideoCallRepository()
        }
        return instance
    }

}