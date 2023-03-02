package mdy.klt.myatmyat.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mdy.klt.myatmyat.common.Constants
import mdy.klt.myatmyat.data.HistoryDatabase
import mdy.klt.myatmyat.repository.HistoryRepository
import mdy.klt.myatmyat.repository.HistoryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDb(
        @ApplicationContext context: Context
    ): HistoryDatabase {
        return Room.databaseBuilder(
            context,
            HistoryDatabase::class.java,
            Constants.DB_NAME
        )
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesRepo(
        db: HistoryDatabase
    ): HistoryRepository {
        return HistoryRepositoryImpl(
            db = db
        )
    }
}