package com.example.thenamegame.repository

import android.util.Log
import com.example.thenamegame.model.Profile
import com.example.thenamegame.model.ResultWrapper
import com.example.thenamegame.network.ProfileApi
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getAll(): ResultWrapper<ArrayList<Profile>, Boolean, Exception> {
        val resultWrapper = ResultWrapper<ArrayList<Profile>, Boolean, Exception>()
        try {
            resultWrapper.data = api.getAll()
        } catch (e: Exception) {
            resultWrapper.exception = e
            Log.e(TAG, "getAll: ${e.localizedMessage}")
        }
        return resultWrapper
    }

    private companion object {
        private const val TAG = "ProfileRepositoryImp"
    }

}