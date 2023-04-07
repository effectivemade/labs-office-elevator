package cafe.adriel.voyager.core.model

import androidx.compose.runtime.DisallowComposableCalls
import cafe.adriel.voyager.core.concurrent.ThreadSafeMap
import cafe.adriel.voyager.core.platform.multiplatformName
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow

private typealias ScreenModelKey = String

private typealias DependencyKey = String
private typealias DependencyInstance = Any
private typealias DependencyOnDispose = (Any) -> Unit
private typealias Dependency = Pair<DependencyInstance, DependencyOnDispose>

internal object ScreenModelStore {

    @PublishedApi
    internal val screenModels: MutableMap<ScreenModelKey, ScreenModel> = ThreadSafeMap()

    @PublishedApi
    internal val dependencies: MutableMap<DependencyKey, Dependency> = ThreadSafeMap()

    @PublishedApi
    internal val lastScreenModelKey: MutableStateFlow<ScreenModelKey?> = MutableStateFlow(null)

    internal inline fun <reified T : ScreenModel> getKey(
        screen: Screen,
        tag: String?
    ): ScreenModelKey =
        "${screen.key}:${T::class.multiplatformName}:${tag ?: "default"}"

    @PublishedApi
    internal fun getDependencyKey(screenModel: ScreenModel, name: String): DependencyKey =
        screenModels
            .firstNotNullOfOrNull {
                if (it.value == screenModel) it.key
                else null
            }
            ?: lastScreenModelKey.value
                ?.let { "$it:$name" }
            ?: "standalone:$name"

    internal inline fun <reified T : ScreenModel> getOrPut(
        screen: Screen,
        tag: String?,
        factory: @DisallowComposableCalls () -> T
    ): T {
        val key = getKey<T>(screen, tag)
        lastScreenModelKey.value = key
        return screenModels.getOrPut(key, factory) as T
    }

    internal inline fun <reified T : Any> getOrPutDependency(
        screenModel: ScreenModel,
        name: String,
        noinline onDispose: @DisallowComposableCalls (T) -> Unit = {},
        noinline factory: @DisallowComposableCalls (DependencyKey) -> T
    ): T {
        val key = getDependencyKey(screenModel, name)

        return dependencies
            .getOrPut(key) { (factory(key) to onDispose) as Dependency }
            .first as T
    }

    internal fun remove(screen: Screen) {
        screenModels.onEach(screen) { key ->
            screenModels[key]?.onDispose()
            screenModels -= key
        }

        dependencies.onEach(screen) { key ->
            dependencies[key]?.let { (instance, onDispose) -> onDispose(instance) }
            dependencies -= key
        }
    }

    private fun Map<String, *>.onEach(screen: Screen, block: (String) -> Unit) =
        asSequence()
            .filter { it.key.startsWith(screen.key) }
            .map { it.key }
            .forEach(block)
}
