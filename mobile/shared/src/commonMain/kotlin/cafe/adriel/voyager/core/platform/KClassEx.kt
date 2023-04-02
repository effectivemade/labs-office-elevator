package cafe.adriel.voyager.core.platform

import kotlin.reflect.KClass

internal expect val KClass<*>.multiplatformName: String?
