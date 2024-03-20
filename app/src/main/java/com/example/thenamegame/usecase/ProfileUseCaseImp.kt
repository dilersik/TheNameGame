package com.example.thenamegame.usecase

import android.util.Log
import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper
import com.example.thenamegame.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProfileUseCaseImp @Inject constructor(
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val profileRepository: ProfileRepository
) : ProfileUseCase {

    override suspend fun getAll(): ResultWrapper<List<Profile>?, Boolean, Exception> = withContext(coroutineContext) {
        val resultWrapper = ResultWrapper<List<Profile>?, Boolean, Exception>()
        val result = profileRepository.getAll()
        return@withContext if (result.data?.isNotEmpty() == true) {
            resultWrapper.data = result.data
            resultWrapper
        } else {
            resultWrapper.exception = Exception("No profiles found")
            resultWrapper
        }
    }

    override suspend fun getRandomProfilesWithOneName(profiles: List<Profile>): Pair<List<Profile>, String> =
        withContext(coroutineContext) {
            val randomProfiles = profiles.shuffled().take(6)
            val randomProfile = randomProfiles.random()
            Log.d("ProfileUseCaseImp", randomProfile.headshot.url.toString())
            Pair(randomProfiles, randomProfile.getFullName())
        }

}