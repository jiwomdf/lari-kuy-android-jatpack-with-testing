package com.programmergabut.larikuy.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.programmergabut.larikuy.db.RunningDatabase
import com.programmergabut.larikuy.other.Constants
import com.programmergabut.larikuy.ui.fragments.TestMainFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db") //dont forget to use the name to distinguish between the application DB dagger and our Test DB dagger
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, RunningDatabase::class.java)
            .allowMainThreadQueries()
            .build()


    @Provides
    @Singleton
    @Named("test_sharedPref")
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    @Named("test_sharedPref_name")
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(Constants.KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    @Named("test_sharedPref_weight")
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(Constants.KEY_WEIGHT, 55f) ?: 55f

    @Singleton
    @Provides
    @Named("test_sharedPref_firstTime")
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) = sharedPref.getBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)
}