package cafe.adriel.voyager.core.concurrent

expect class AtomicInt32(initialValue: Int) {
    public fun getAndIncrement(): Int
}
