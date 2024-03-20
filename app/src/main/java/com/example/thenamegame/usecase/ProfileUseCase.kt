package com.example.thenamegame.usecase

import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper

interface ProfileUseCase {
    suspend fun getAll(): ResultWrapper<List<Profile>?, Boolean, Exception>
    suspend fun getRandomProfilesWithOneName(profiles: List<Profile>): Pair<List<Profile>, String>
}