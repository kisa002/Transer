package com.haeyum.shared.presentation.vector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector


public val Icons.Outlined.History: ImageVector
    get() {
        if (_history != null) {
            return _history!!
        }
        _history = materialIcon(name = "Outlined.Settings") {
            materialPath {
                moveTo(13.0F, 3.0F)
                curveToRelative(-4.97F, 0.0F, -9.0F, 4.03F, -9.0F, 9.0F)
                lineTo(1.0F, 12.0F)
                lineToRelative(3.89F, 3.89F)
                lineToRelative(0.07F, 0.14F)
                lineTo(9.0F, 12.0F)
                lineTo(6.0F, 12.0F)
                curveToRelative(0.0F, -3.87F, 3.13F, -7.0F, 7.0F, -7.0F)
                reflectiveCurveToRelative(7.0F, 3.13F, 7.0F, 7.0F)
                reflectiveCurveToRelative(-3.13F, 7.0F, -7.0F, 7.0F)
                curveToRelative(-1.93F, 0.0F, -3.68F, -0.79F, -4.94F, -2.06F)
                lineToRelative(-1.42F, 1.42F)
                curveTo(8.27F, 19.99F, 10.51F, 21.0F, 13.0F, 21.0F)
                curveToRelative(4.97F, 0.0F, 9.0F, -4.03F, 9.0F, -9.0F)
                reflectiveCurveToRelative(-4.03F, -9.0F, -9.0F, -9.0F)
                close()
                moveTo(12.0F, 8.0F)
                verticalLineToRelative(5.0F)
                lineToRelative(4.25F, 2.52F)
                lineToRelative(0.77F, -1.28F)
                lineToRelative(-3.52F, -2.09F)
                lineTo(13.5F, 8.0F)
                close()
            }
        }
        return _history!!
    }

private var _history: ImageVector? = null