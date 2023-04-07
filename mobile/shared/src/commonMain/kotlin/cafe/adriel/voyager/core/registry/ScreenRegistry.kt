package cafe.adriel.voyager.core.registry

import cafe.adriel.voyager.core.concurrent.ThreadSafeMap
import cafe.adriel.voyager.core.platform.multiplatformName
import cafe.adriel.voyager.core.screen.Screen
import kotlin.reflect.KClass

private typealias ProviderKey = KClass<out ScreenProvider>

private typealias ScreenFactory = (ScreenProvider) -> Screen

internal object ScreenRegistry {

    @PublishedApi
    internal val factories: ThreadSafeMap<ProviderKey, ScreenFactory> = ThreadSafeMap()

    public operator fun invoke(block: ScreenRegistry.() -> Unit) {
        this.block()
    }

    internal inline fun <reified T : ScreenProvider> register(noinline factory: (T) -> Screen) {
        factories[T::class] = factory as ScreenFactory
    }

    internal fun get(provider: ScreenProvider): Screen {
        val factory = factories[provider::class]
            ?: error("ScreenProvider not registered: ${provider::class.multiplatformName}")
        return factory(provider)
    }
}
