package band.effective.office.elevator.expects

import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.seiko.imageloader.component.ComponentRegistryBuilder
import okio.Path

expect fun showToast(message: String)

expect fun generateVibration(milliseconds: Long)

expect fun makeCall(phoneNumber: String)


expect fun pickTelegram(telegramNick: String)

expect fun pickSBP(phoneNumber: String, bankInfo: SBPBankInfo)

expect fun isApplicationInstalled(applicationId: String?) : Boolean

expect fun getApplicationBankId(bankInfo: SBPBankInfo) : String?

internal expect fun ComponentRegistryBuilder.setupDefaultComponents()

internal expect fun getImageCacheDirectoryPath(): Path