package band.effective.office.elevator.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import band.effective.office.elevator.android.theme.setupThemedNavigation
import band.effective.office.elevator.common.compose.NavigationTree
import band.effective.office.elevator.common.compose.navigation.navigationGraph

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupThemedNavigation(NavigationTree.Actions.name) { navigationGraph() }
    }
}
