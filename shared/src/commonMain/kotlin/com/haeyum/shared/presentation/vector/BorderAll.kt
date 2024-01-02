package com.haeyum.shared.presentation.vector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Outlined.BorderAll: ImageVector
    get() {
        if (_borderAll != null) {
            return _borderAll!!
        }
        _borderAll = materialIcon(name = "Outlined.BorderAll") {
            materialPath {
                moveTo(3.0F, 3.0F)
                verticalLineToRelative(18.0F)
                horizontalLineToRelative(18.0F)
                lineTo(21.0F, 3.0F)
                lineTo(3.0F, 3.0F)
                close()
                moveTo(11.0F, 19.0F)
                lineTo(5.0F, 19.0F)
                verticalLineToRelative(-6.0F)
                horizontalLineToRelative(6.0F)
                verticalLineToRelative(6.0F)
                close()
                moveTo(11.0F, 11.0F)
                lineTo(5.0F, 11.0F)
                lineTo(5.0F, 5.0F)
                horizontalLineToRelative(6.0F)
                verticalLineToRelative(6.0F)
                close()
                moveTo(19.0F, 19.0F)
                horizontalLineToRelative(-6.0F)
                verticalLineToRelative(-6.0F)
                horizontalLineToRelative(6.0F)
                verticalLineToRelative(6.0F)
                close()
                moveTo(19.0F, 11.0F)
                horizontalLineToRelative(-6.0F)
                lineTo(13.0F, 5.0F)
                horizontalLineToRelative(6.0F)
                verticalLineToRelative(6.0F)
                close()
            }
        }
        return _borderAll!!
    }

private var _borderAll: ImageVector? = null