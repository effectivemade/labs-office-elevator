package band.effective.office.elevator.android

import android.content.Intent
import android.os.Bundle
import band.effective.office.elevator.common.compose.Android
import band.effective.office.elevator.common.compose.RootView
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Android.context = this
        Android.activity = this
        setContent { RootView() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GoogleAuthorization.activityResult(requestCode, resultCode, data)
    }
}
