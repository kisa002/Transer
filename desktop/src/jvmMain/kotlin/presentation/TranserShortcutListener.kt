package presentation

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener

class TranserShortcutListener(val onEscKeyPressed: () -> Unit, val onTriggerKeyPressed: () -> Unit) : NativeKeyListener {
    var isPressedAlt = false
    var isPressedSpace = false

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        when (nativeEvent?.keyCode) {
            NativeKeyEvent.VC_ESCAPE -> onEscKeyPressed()
            NativeKeyEvent.VC_ALT -> isPressedAlt = true
            NativeKeyEvent.VC_SPACE -> isPressedSpace = true
        }

        if (isPressedAlt && isPressedSpace)
            onTriggerKeyPressed()
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
        when (nativeEvent?.keyCode) {
            NativeKeyEvent.VC_ALT -> isPressedAlt = false
            NativeKeyEvent.VC_SPACE -> isPressedSpace = false
        }
    }
}