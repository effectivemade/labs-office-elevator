package cafe.adriel.voyager.core.concurrent

internal expect class ThreadSafeMap<K, V>() : MutableMap<K, V>
