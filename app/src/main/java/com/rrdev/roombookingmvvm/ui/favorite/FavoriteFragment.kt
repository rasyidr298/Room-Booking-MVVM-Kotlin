package com.rrdev.roombookingmvvm.ui.favorite

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
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.favorite_fragment.*

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val repository = RoomRepository(api,db)
        val factory = FavoritelViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel::class.java)
        //bindUI()
    }

//    private fun bindUI() = Coroutines.main{
//        viewModel.getFavorite().observe(this, Observer {
//            initRecyclerView(it.toFavoriteItem())
//        })
//    }
//
//    private fun initRecyclerView(favoriteItem: List<FavoriteItem>) {
//        val mAdapter = GroupAdapter<ViewHolder>().apply {
//            addAll(favoriteItem)
//        }
//
//        rvFavorite.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = mAdapter
//        }
//
//        mAdapter.setOnItemClickListener{items, view->
//            (items as? FavoriteItem)?.let {
//                showRoomDetail(it.roomsFavorite.namaRoom,view)
//            }
//        }
//    }
//
//    private fun showRoomDetail(namaRoom: String, view: View){
//        val actionDetail = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(namaRoom)
//        Navigation.findNavController(view).navigate(actionDetail)
//    }
//
//    private fun List<Rooms>.toFavoriteItem() : List<FavoriteItem>{
//        return this.map {
//            FavoriteItem(it)
//        }
//    }

}
