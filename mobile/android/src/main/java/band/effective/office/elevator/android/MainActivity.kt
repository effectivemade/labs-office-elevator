package band.effective.office.elevator.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import band.effective.office.elevator.common.compose.RootView
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RootView() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GoogleAuthorization.activityResult(requestCode, resultCode, data)
    }
}
