package com.haeyum.shared.presentation.vector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Filled.ContentCopy: ImageVector
    get() {
        if (_currentCopy != null) {
            return _currentCopy!!
        }
        _currentCopy = materialIcon(name = "Filled.ContentCopy") {
            materialPath {
                moveTo(16.0F, 1.0F)
                lineTo(4.0F, 1.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(14.0F)
                horizontalLineToRelative(2.0F)
                lineTo(4.0F, 3.0F)
                horizontalLineToRelative(12.0F)
                lineTo(16.0F, 1.0F)
                close()
                moveTo(19.0F, 5.0F)
                lineTo(8.0F, 5.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(14.0F)
                curveToRelative(0.0F, 1.1F, 0.9F, 2.0F, 2.0F, 2.0F)
                horizontalLineToRelative(11.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                lineTo(21.0F, 7.0F)
                curveToRelative(0.0F, -1.1F, -0.9F, -2.0F, -2.0F, -2.0F)
                close()
                moveTo(19.0F, 21.0F)
                lineTo(8.0F, 21.0F)
                lineTo(8.0F, 7.0F)
                horizontalLineToRelative(11.0F)
                verticalLineToRelative(14.0F)
                close()
            }
        }
        return _currentCopy!!
    }

private var _currentCopy: ImageVector? = null