package band.effective.office.elevator.android

import android.os.Bundle
import band.effective.office.elevator.common.compose.RootView
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { RootView() }
    }
}
