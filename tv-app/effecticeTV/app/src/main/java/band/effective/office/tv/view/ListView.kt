package band.effective.office.tv.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.tv.material3.Text

@Composable
fun ListView(viewModel: ListViewModel){
    val response by viewModel.apiResponse.collectAsState()
    if (response!=null){
        LazyColumn(){
            items(7){i ->
                val item = response!!.data.items[i]
                Text(item.full_name)
                Text(item.format)
            }
        }
    }
}