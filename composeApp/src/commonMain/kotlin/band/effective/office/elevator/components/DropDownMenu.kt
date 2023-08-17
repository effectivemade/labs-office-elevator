package band.effective.office.elevator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset

@Composable
fun DropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    offset: DpOffset,
){
    var  interactionSource = remember { MutableInteractionSource() }
    AnimatedVisibility(
        enter = fadeIn(),
        modifier = Modifier,
        exit = fadeOut(),
        visible = expanded,
        content ={
            Box(modifier = modifier.fillMaxSize()
                .clickable(onClick = onDismissRequest,
                    indication = null,
                    interactionSource = interactionSource)
            ) {
                Column(modifier = Modifier.clickable (onClick = {},
                    indication = null,
                    interactionSource = interactionSource))
                {
                    content()
                }
            }
        }
    )
}