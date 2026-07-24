package io.github.ranzlappen.template.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.ranzlappen.template.core.common.coroutines.Dispatcher
import io.github.ranzlappen.template.core.common.coroutines.TemplateDispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/** Application-lifetime scope for work that must outlive a single screen. */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun providesApplicationScope(
        @Dispatcher(TemplateDispatchers.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
