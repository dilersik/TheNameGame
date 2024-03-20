package com.example.thenamegame.usecase

import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper

interface ProfileUseCase {
    suspend fun getRandomProfilesWithOneName(): ResultWrapper<Pair<List<Profile>, String>?, Boolean, Exception>
}