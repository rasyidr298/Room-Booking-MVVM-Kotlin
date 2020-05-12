package com.rrdev.roombookingmvvm.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.rrdev.mvvmtrial.data.db.AppDatabase

import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.ui.home.detail.DetailActivity
import com.rrdev.roombookingmvvm.ui.profile.ProfileViewModelFactory
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.rrdev.roombookingmvvm.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val repository = RoomRepository(api,db)
        val factory = HomeViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this , factory).get(HomeViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = Coroutines.main{
        pbHome.show()
        viewModel.room.await().observe(this, Observer {
            pbHome.hide()
            initRecyclerView(it.toHomeItem())
        })
    }

    private fun initRecyclerView(homeItem: List<HomeItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(homeItem)
        }

        rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

        mAdapter.setOnItemClickListener{items, view->
            (items as? HomeItem)?.let {
                showRoomDetail(it.rooms.namaRoom,view)
            }
        }
    }

    private fun showRoomDetail(namaRoom: String, view: View){
        val actionDetail = HomeFragmentDirections.actionDetail(namaRoom)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun List<Rooms>.toHomeItem() : List<HomeItem>{
        return this.map {
            HomeItem(it)
        }
    }

}
