package com.rrdev.roombookingmvvm.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.BASE
import com.rrdev.roombookingmvvm.util.Coroutines
import kotlinx.android.synthetic.main.content_fragment_detail_room.*
import kotlinx.android.synthetic.main.fragment_detail_room.*
import kotlinx.android.synthetic.main.fragment_profile.*

class DetailRoomFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_room, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let {DetailRoomFragmentArgs.fromBundle(it).namaRoom}
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val repository = RoomRepository(api, db)
        val factory = DetailViewModelFactory(safeArgs.toString(),repository)

        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)

        toBooking()
        getDetailRoom()
    }

    private fun toBooking() {
        fabBooking.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_bookingRoomFragment)
        }
    }

    private fun getDetailRoom() {
        Coroutines.main {
            val detailRoom = viewModel.detailRoom.await()
            detailRoom.observe(viewLifecycleOwner, Observer {
                tvNamaRoomDetailAct.text = it.namaRoom
                tvKapasitasRoomHome.text = it.kapasitas.toString()
                tvFasilitas1.text = it.fasilitas1
                tvFasilitas2.text = it.fasilitas2
                tvFasilitas3.text = it.fasilitas3
                tvFasilitas4.text = it.fasilitas4
                tvDeskripsi.text = it.deskripsi
                val imageRoom = it.image
                Glide.with(this).load(BASE+imageRoom).into(det_gambar)
            })
        }
    }
}
