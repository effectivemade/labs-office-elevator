package band.effective.office.tv.screen.menu.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.effecticetv.ui.theme.robotoFontFamily
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun TimeComponent(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = SimpleDateFormat("EEE, d MMMM HH:mm").format(GregorianCalendar().time),
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = robotoFontFamily()
        )
    }
}