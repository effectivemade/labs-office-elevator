package cafe.adriel.voyager.core.concurrent

import java.util.concurrent.CopyOnWriteArraySet

internal actual class ThreadSafeSet<T> : MutableSet<T> by CopyOnWriteArraySet()
