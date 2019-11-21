package praneeth.com.sportsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jetbrains.anko.find
import praneeth.com.sportsapp.R
import praneeth.com.sportsapp.domain.dataModels.Player
import praneeth.com.sportsapp.util.bindOrHideWhenNull

/**
 * Created by Praneeth on 2019-11-19.
 */

class PlayersAdapter(private val players: List<Player>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (players.isNullOrEmpty()) {
            return
        }
        holder.bindData(players[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerImage = itemView.find<AppCompatImageView>(R.id.image_player)
        private val playerName = itemView.find<AppCompatTextView>(R.id.text_name)
        private val playerNameDescription = itemView.find<AppCompatTextView>(R.id.text_player_name)
        private val playerDateSigned = itemView.find<AppCompatTextView>(R.id.text_date)
        private val playerDateSignedDescription = itemView.find<AppCompatTextView>(R.id.text_date_signed)

        fun bindData(player: Player, listener: OnItemClickListener) {
            with(player) {
                playerName.text = if (strPlayer.isNullOrBlank()) "" else "Name:"
                playerNameDescription.bindOrHideWhenNull(strPlayer)
                playerDateSigned.text = if (dateSigned.isNullOrBlank()) "" else "Date Signed:"
                playerDateSignedDescription.bindOrHideWhenNull(dateSigned)
                playerImage.run {
                    Glide.with(this.context)
                        .load(strThumb)
                        .centerCrop()
                        .placeholder(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_no_image)
                        .fallback(R.drawable.ic_no_image)
                        .into(this)
                }
            }

            itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onItemClicked(player)
                }
            })
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(player: Player)
    }
}
