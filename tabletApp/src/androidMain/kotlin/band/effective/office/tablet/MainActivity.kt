package band.effective.office.tablet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import band.effective.office.tablet.di.initRoomInfoKoin
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRoomInfoKoin()
        setContent {
            App(defaultComponentContext(),DefaultStoreFactory())
        }
    }
}