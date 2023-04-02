package cafe.adriel.voyager.core.registry

private typealias ScreenModule = ScreenRegistry.() -> Unit

internal fun screenModule(block: ScreenModule): ScreenModule =
    { block() }
