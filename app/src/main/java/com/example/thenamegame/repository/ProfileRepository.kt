package com.example.thenamegame.repository

import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper

interface ProfileRepository {
    suspend fun getAll(forceApi: Boolean = false): ResultWrapper<ArrayList<Profile>, Boolean, Exception>
}