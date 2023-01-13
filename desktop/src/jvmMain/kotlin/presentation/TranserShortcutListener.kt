package presentation

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import supports.CurrentPlatform

class TranserShortcutListener(val onEscKeyPressed: () -> Unit, val onTriggerKeyPressed: () -> Unit) : NativeKeyListener {
    var isPressedAlt = false
    var isPressedSpace = false

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        if (CurrentPlatform.isMac) {
            when (nativeEvent?.keyCode) {
                NativeKeyEvent.VC_ESCAPE -> onEscKeyPressed()
                NativeKeyEvent.VC_ALT -> isPressedAlt = true
                NativeKeyEvent.VC_SPACE -> isPressedSpace = true
            }
        } else {
            when (nativeEvent?.keyCode) {
                NativeKeyEvent.VC_ESCAPE -> onEscKeyPressed()
                NativeKeyEvent.VC_CONTROL -> isPressedAlt = true
                NativeKeyEvent.VC_SPACE -> isPressedSpace = true
            }
        }

        if (isPressedAlt && isPressedSpace)
            onTriggerKeyPressed()
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
        if (CurrentPlatform.isMac) {
            when (nativeEvent?.keyCode) {
                NativeKeyEvent.VC_ALT -> isPressedAlt = false
                NativeKeyEvent.VC_SPACE -> isPressedSpace = false
            }
        } else {
            when (nativeEvent?.keyCode) {
                NativeKeyEvent.VC_CONTROL -> isPressedAlt = false
                NativeKeyEvent.VC_SPACE -> isPressedSpace = false
            }
        }
    }
}