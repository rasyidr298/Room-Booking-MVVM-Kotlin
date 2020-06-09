package com.rrdev.roombookingmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.adapter.RoomsAdapter
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.network.responses.RoomResponse
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.Resource
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
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
        viewModel.room.await().observe(viewLifecycleOwner ,Observer {
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
