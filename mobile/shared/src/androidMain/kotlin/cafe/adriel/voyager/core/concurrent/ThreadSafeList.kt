package cafe.adriel.voyager.core.concurrent

import java.util.concurrent.CopyOnWriteArrayList

internal actual class ThreadSafeList<T> : MutableList<T> by CopyOnWriteArrayList()
