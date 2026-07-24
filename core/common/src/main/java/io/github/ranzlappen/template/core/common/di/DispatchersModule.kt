package io.github.ranzlappen.template.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.ranzlappen.template.core.common.coroutines.Dispatcher
import io.github.ranzlappen.template.core.common.coroutines.TemplateDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(TemplateDispatchers.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(TemplateDispatchers.IO)
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
