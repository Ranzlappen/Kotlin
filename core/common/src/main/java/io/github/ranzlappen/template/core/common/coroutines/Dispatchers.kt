package io.github.ranzlappen.template.core.common.coroutines

import javax.inject.Qualifier

/** Hilt qualifier so consumers inject dispatchers instead of hardcoding them. */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(
    val dispatcher: TemplateDispatchers,
)

enum class TemplateDispatchers {
    Default,
    IO,
}
