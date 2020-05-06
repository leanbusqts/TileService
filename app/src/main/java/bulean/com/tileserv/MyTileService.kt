package bulean.com.tileserv

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import bulean.com.tileserv.util.Loggable
import bulean.com.tileserv.util.info

class MyTileService: TileService(), Loggable {

    private val icTileFull by lazy { Icon.createWithResource(this, R.drawable.ic_adb_black_24dp) }
    private val icTileEmpty by lazy { Icon.createWithResource(this, R.drawable.ic_adb_white_24dp) }

    //private var isRemovingTile = false

    /*
    Tile:
    A Tile holds the state of a tile that will be displayed in Quick Settings.
    A tile in Quick Settings exists as an icon with an accompanied label.
    https://developer.android.com/reference/android/service/quicksettings/Tile
    */
    /*
    TileService
    A TileService provides the user a tile that can be added to Quick Settings.
    Quick Settings is a space provided that allows the user to change settings
    and take quick actions without leaving the context of their current app.
    https://developer.android.com/reference/android/service/quicksettings/TileService
    */

    // onTileAdded(): Called when the user adds this tile to Quick Settings.
    override fun onTileAdded() {
        //isRemovingTile = false
        updateTile()
        info("tile added")
        super.onTileAdded()
    }
    // onStartListening(): Called when this tile moves into a listening state.
    override fun onStartListening() {
        info("started listening")
        /* isLocked
         Checks if the lock screen is showing. When a device is locked,
         then showDialog(Dialog) will not present a dialog, as it will be under the lock screen. */
        if(isLocked) {
            info("phone is locked")
            updateTile(state = Tile.STATE_UNAVAILABLE)
            return
        }
        super.onStartListening()
    }
    // onClick(): Called when the user clicks on this tile.
    override fun onClick() {
        info("tile clicked")
        onTick()
        super.onClick()
    }
    /*
    // onStopListening(): Called when this tile moves out of the listening state.
    override fun onStopListening() {
        info("stopped listening")
        super.onStopListening()
    }*/
    /*
    // onTileRemoved(): Called when the user removes this tile from Quick Settings.
    override fun onTileRemoved() {
        info("tile removed")
        isRemovingTile = true
        super.onTileRemoved()
    }*/

    private fun updateTile(
            state: Int = Tile.STATE_INACTIVE,
            icon: Icon = icTileEmpty) {

        qsTile ?: return
        qsTile.state = state
        qsTile.icon = icon
        info("updating tile")
        qsTile.updateTile()
    }
    private fun onTick(){
        if(qsTile.state == Tile.STATE_ACTIVE){
            updateTile()
            toast("off")
        }
        else{
            updateTile(Tile.STATE_ACTIVE, icTileFull)
            toast("on")
        }
    }
    private fun toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}