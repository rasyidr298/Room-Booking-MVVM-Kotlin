package com.rrdev.roombookingmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import kotlinx.android.synthetic.main.item_home.view.*

class RoomsAdapter: RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Rooms>(){
        override fun areItemsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem.idRoom == newItem.idRoom
        }

        override fun areContentsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = differ.currentList[position]
        holder.itemView.apply {
           // Glide.with(this).load(room.deskripsi).into(ivRoomHome)
            tvNamaRoomHome.text = room.namaRoom
            tvKapasitasRoomHome.text = room.kapasitas.toString()
            setOnClickListener {
                onItemClickListener?.let { it(room) }
            }
        }
    }

    private var onItemClickListener: ((Rooms) -> Unit)? = null

    fun setOnItemClickListener(listener: (Rooms) -> Unit){
        onItemClickListener = listener
    }
}