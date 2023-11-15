package band.effective.office.utils.buildUtils

import effective_office.contract.BuildConfig

actual fun params(): BuildType = if (BuildConfig.isDebug) BuildType.DEBUG else BuildType.RELEASE