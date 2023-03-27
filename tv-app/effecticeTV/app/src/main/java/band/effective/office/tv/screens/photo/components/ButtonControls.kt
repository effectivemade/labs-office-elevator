package band.effective.office.tv.screens.photo.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource

@Composable
fun ButtonControls(
    isFocus: Boolean,
    idActiveIcon: Int,
    idInactiveIcon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(
            if (isFocus) idActiveIcon
            else idInactiveIcon
        ),
        contentDescription = null,
        modifier = modifier
            .clickable { onClick() }
    )
}