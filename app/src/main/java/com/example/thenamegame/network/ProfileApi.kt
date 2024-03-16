package com.example.thenamegame.network

import com.example.thenamegame.model.ProfileRoot
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ProfileApi {

    @GET("profiles")
    suspend fun getAll(): ProfileRoot

}