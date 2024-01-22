package band.effective.office.tablet.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    val postfix = if (false) "-test" else "" //TODO
    single(named("FireBaseTopics")) { listOf("workspace", "user", "booking").map { it + postfix } }
}