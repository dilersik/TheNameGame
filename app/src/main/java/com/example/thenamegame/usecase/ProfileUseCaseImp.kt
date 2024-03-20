package com.example.thenamegame.usecase

import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper
import com.example.thenamegame.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCaseImp @Inject constructor(private val profileRepository: ProfileRepository) : ProfileUseCase {

    override suspend fun getRandomProfilesWithOneName(): ResultWrapper<Pair<List<Profile>, String>?, Boolean, Exception> {
        val resultWrapper = ResultWrapper<Pair<List<Profile>, String>?, Boolean, Exception>()
        val result = profileRepository.getAll()
        return if (result.data?.isNotEmpty() == true) {
            val randomProfiles = result.data!!.shuffled().take(6)
            val randomProfile = randomProfiles.random()
            resultWrapper.data = Pair(randomProfiles, randomProfile.getFullName())
            resultWrapper
        } else {
            resultWrapper.exception = Exception("No profiles found")
            resultWrapper
        }
    }

}