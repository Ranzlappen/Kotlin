package io.github.ranzlappen.template.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.ranzlappen.template.core.common.coroutines.Dispatcher
import io.github.ranzlappen.template.core.common.coroutines.TemplateDispatchers
import io.github.ranzlappen.template.core.common.di.ApplicationScope
import io.github.ranzlappen.template.core.data.prefs.DataStoreUserPreferencesRepository
import io.github.ranzlappen.template.core.data.prefs.UserPreferencesRepository
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsUserPreferencesRepository(
        impl: DataStoreUserPreferencesRepository,
    ): UserPreferencesRepository

    companion object {
        @Provides
        @Singleton
        fun providesPreferencesDataStore(
            @ApplicationContext context: Context,
            @ApplicationScope scope: CoroutineScope,
            @Dispatcher(TemplateDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
            scope = scope.plus(ioDispatcher),
        ) {
            context.preferencesDataStoreFile("user_preferences")
        }
    }
}
