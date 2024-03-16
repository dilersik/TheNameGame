package com.example.thenamegame.di

import com.example.thenamegame.network.ProfileApi
import com.example.thenamegame.repository.ProfileRepository
import com.example.thenamegame.repository.ProfileRepositoryImp
import com.example.thenamegame.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProfileApi(): ProfileApi = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideProfileRepository(profileApi: ProfileApi): ProfileRepository = ProfileRepositoryImp(profileApi)

}