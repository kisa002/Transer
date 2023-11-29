package com.haeyum.shared.presentation.vector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Filled.ArrowCircleRight: ImageVector
    get() {
        if (_arrowCircleRight != null) {
            return _arrowCircleRight!!
        }
        _arrowCircleRight = materialIcon(name = "Filled.ArrowCircleRight") {
            materialPath {
                moveTo(22.0F, 12.0F)
                curveToRelative(0.0F, -5.52F, -4.48F, -10.0F, -10.0F, -10.0F)
                reflectiveCurveTo(2.0F, 6.48F, 2.0F, 12.0F)
                curveToRelative(0.0F, 5.52F, 4.48F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveTo(22.0F, 17.52F, 22.0F, 12.0F)
                close()
                moveTo(12.0F, 13.0F)
                lineToRelative(-4.0F, 0.0F)
                verticalLineToRelative(-2.0F)
                lineToRelative(4.0F, 0.0F)
                verticalLineTo(8.0F)
                lineToRelative(4.0F, 4.0F)
                lineToRelative(-4.0F, 4.0F)
                verticalLineTo(13.0F)
                close()
            }
        }
        return _arrowCircleRight!!
    }

private var _arrowCircleRight: ImageVector? = null