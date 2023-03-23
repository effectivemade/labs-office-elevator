package band.effective.office.tv.screens.photo.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.screens.photo.model.Photo

@Composable
fun PhotoSlideShow(
    photos: List<Photo>,
    lazyListState: TvLazyListState = rememberTvLazyListState(),
    modifier: Modifier = Modifier,
    modifierForFocus: Modifier = Modifier
) {
    TvLazyRow(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .focusable()
    ) {
        items(photos) { photo ->
            PhotoUIItem(
                image = photo,
                modifier = modifierForFocus
                    .fillMaxSize()
                    .focusable()
            )
        }
    }

}