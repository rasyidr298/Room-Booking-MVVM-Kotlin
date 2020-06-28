package com.rrdev.roombookingmvvm.ui.detailRoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.util.BASE
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.hide
import kotlinx.android.synthetic.main.content_fragment_detail_room.*
import kotlinx.android.synthetic.main.fragment_detail_room.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailRoomFragment : Fragment(),KodeinAware {

    override val kodein by kodein()
    private val factory: DetailViewModelFactory by instance<DetailViewModelFactory>()
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_room, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar_detail_room)}
        
        toBooking()
        getDetailRoom()
        setupToolbar()
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
                tvKapasitasRoomHome.text = "Kapasitas "+it.kapasitas.toString()
                tvFasilitas1.text = it.fasilitas1
                tvFasilitas2.text = it.fasilitas2
                tvFasilitas3.text = it.fasilitas3
                tvFasilitas4.text = it.fasilitas4
                tvDeskripsi.text = it.deskripsi
                val imageRoom = it.image
                Glide.with(this).load(BASE+imageRoom).into(det_gambar)
                fabDetail.hide()
            })
        }
    }

    private fun setupToolbar(){
        (activity as AppCompatActivity).supportActionBar?.title = "Detail Room"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}
