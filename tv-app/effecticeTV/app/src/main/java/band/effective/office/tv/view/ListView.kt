package band.effective.office.tv.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ListView(){
    val viewModel: ListViewModel = hiltViewModel()
    val response by viewModel.apiResponse.collectAsState()
    if (response!=null){
        LazyColumn(){
            items(7){i ->
                val item = response!!.data.items[i]
                Text(item.fullName)
                Text(item.format)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.photo)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.fullName
                )
            }
        }
    }
}