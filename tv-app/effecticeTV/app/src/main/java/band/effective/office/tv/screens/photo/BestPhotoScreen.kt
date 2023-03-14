package band.effective.office.tv.screens.photo

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BestPhotoScreen() {
    val viewModel: PhotoViewModel = hiltViewModel()
    Button(onClick = {viewModel.test()}) {
        Text(text = "click")
    }
}