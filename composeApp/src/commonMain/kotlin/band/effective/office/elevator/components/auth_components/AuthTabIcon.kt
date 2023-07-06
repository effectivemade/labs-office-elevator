package band.effective.office.elevator.components.auth_components

import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.theme_light_tertiary_icon_color

internal val AuthTabIcon : ImageVector
    get() {
        if (auth_tab_icon != null) {
            return auth_tab_icon!!
        }
        auth_tab_icon = ImageVector.Builder(
            name = "AuthTabIcon", defaultWidth = 102.0.dp, defaultHeight = 4.0.dp,
            viewportWidth = 102.0f, viewportHeight = 4.0f
        ).apply {
            path(fill = SolidColor(theme_light_tertiary_icon_color), stroke = null, fillAlpha = 0.4f,
                strokeLineWidth = 0.0f, strokeLineCap = StrokeCap.Butt, strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f, pathFillType = PathFillType.NonZero
            ) {
                moveTo(2.333f, 0.0f)
                lineTo(99.666f, 0.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 101.666f, 2.0f)
                lineTo(101.666f, 2.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 99.666f, 4.0f)
                lineTo(2.333f, 4.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 0.333f, 2.0f)
                lineTo(0.333f, 2.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 2.333f, 0.0f)
                close()
            }
        }
            .build()
        return auth_tab_icon!!
    }

private var auth_tab_icon: ImageVector? = null