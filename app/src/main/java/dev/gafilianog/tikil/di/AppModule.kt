package dev.gafilianog.tikil.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gafilianog.tikil.BuildConfig
import dev.gafilianog.tikil.data.remote.CypressTikilApiService
import dev.gafilianog.tikil.data.repository.TikilRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideGithubToken(): String = BuildConfig.GITHUB_ACTION_TOKEN

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CypressTikilApiService {
        return retrofit.create(CypressTikilApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTikilRepository(apiService: CypressTikilApiService, githubToken: String): TikilRepository {
        return TikilRepository(apiService)
    }
}