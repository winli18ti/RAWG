package com.winli.rawg.core.di

import androidx.room.Room
import com.winli.rawg.core.BuildConfig
import com.winli.rawg.core.data.GameRepository
import com.winli.rawg.core.data.source.local.LocalDataSource
import com.winli.rawg.core.data.source.local.room.GameDatabase
import com.winli.rawg.core.data.source.remote.RemoteDataSource
import com.winli.rawg.core.data.source.remote.network.ApiService
import com.winli.rawg.core.domain.repository.IGameRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GameDatabase>().gameDao() }
    single {
        val passphrase = SQLiteDatabase.getBytes(KEY.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java, "Game.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOSTNAME, "sha256/R+V29DqDnO269dFhAAB5jMlZHepWpDGuoejXJjprh7A=")
            .add(HOSTNAME, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(HOSTNAME, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IGameRepository> { GameRepository(get(), get()) }
}

private const val HOSTNAME = "api.rawg.io"
private const val BASE_URL = "https://$HOSTNAME/api/"
private const val KEY = BuildConfig.KEY