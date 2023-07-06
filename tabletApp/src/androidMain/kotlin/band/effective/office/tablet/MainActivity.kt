package band.effective.office.tablet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import tablet.di.initSelectRoomKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            initSelectRoomKoin()
            App(defaultComponentContext())
        }
    }
}