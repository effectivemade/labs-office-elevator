package band.effective.office.tv.screens.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import band.effective.office.tv.screens.photo.components.PhotoUIItem

@Composable
fun BestPhotoScreen( viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()
    TvLazyRow{
        items(uiState.photos){ photo ->
            PhotoUIItem(image = photo)
        }
    }
}