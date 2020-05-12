package com.rrdev.roombookingmvvm.ui.home.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.databinding.ActivityDetailBinding
import com.rrdev.roombookingmvvm.util.Coroutines
import kotlinx.android.synthetic.main.activity_detail.*

//private val idRoom: Int = 0
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val safeArgs = DetailActivityArgs.fromBundle(intent?.extras!!).namaRoom
      //  val idRoom = safeArgs!!.idRoom

       // val username = MainActivityArgs.fromBundle(intent?.extras).usernam

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = RoomRepository(api, db)
        val factory = DetailViewModelFactory(safeArgs, repository)

        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
        binding.viewmodel = viewModel

        Coroutines.main {
            val detailRoom = viewModel.detailRoom.await()
            detailRoom.observe(this, Observer {
                tvNamaRoomDetailAct.text = (it.namaRoom)
            })
        }
    }
}
