package com.programmergabut.larikuy.di

import android.content.Context
import androidx.room.Room
import com.programmergabut.larikuy.db.RunningDatabase
import com.programmergabut.larikuy.ui.fragments.TestMainFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db") //dont forget to use the name to distinguish between the application DB dagger and our Test DB dagger
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, RunningDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}