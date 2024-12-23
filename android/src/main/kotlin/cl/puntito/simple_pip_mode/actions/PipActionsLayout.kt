package cl.puntito.simple_pip_mode.actions

import android.app.RemoteAction
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

enum class PipActionsLayout(
    var actions: MutableList<PipAction>
) {
    NONE(mutableListOf()),
    MEDIA(mutableListOf(PipAction.PREVIOUS, PipAction.PAUSE, PipAction.NEXT)),
    MEDIAONLYPAUSE(mutableListOf(PipAction.PAUSE)),
    MEDIALIVE(mutableListOf(PipAction.LIVE, PipAction.PAUSE));

    @RequiresApi(Build.VERSION_CODES.O)
    fun remoteActions(context: Context): MutableList<RemoteAction> =
        remoteActions(context, actions)

    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleToAfterAction(pipAction: PipAction) {
        pipAction.afterAction()?.let { afterAction ->
            val a = actions.firstOrNull{ it == pipAction }
            a?.let {
                val i = actions.indexOf(a)
                actions[i] = afterAction
            }
        }
    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun remoteActions(context: Context, actions: MutableList<PipAction>): MutableList<RemoteAction> =
            actions.map { a -> a.toRemoteAction(context) }.toMutableList()

    }
}