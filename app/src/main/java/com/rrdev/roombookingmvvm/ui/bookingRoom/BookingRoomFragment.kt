package com.rrdev.roombookingmvvm.ui.bookingRoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeRange
import com.akexorcist.snaptimepicker.TimeValue
import com.bumptech.glide.Glide
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.BookingRoomRepository
import com.rrdev.roombookingmvvm.databinding.FragmentBookingRoomBinding
import com.rrdev.roombookingmvvm.util.BASE
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.rrdev.roombookingmvvm.util.snackbar
import kotlinx.android.synthetic.main.item_calender.view.*
import kotlinx.android.synthetic.main.fragment_booking_room.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class BookingRoomFragment : Fragment(),BookingRoomListener {

    private lateinit var viewModel: BookingRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase.invoke(requireContext())
        val repository = BookingRoomRepository(api,db)
        val factory = BookingRoomViewModelFactory(repository)

        val binding: FragmentBookingRoomBinding = DataBindingUtil.inflate(
            inflater,  R.layout.fragment_booking_room,container,false)

        viewModel = ViewModelProviders.of(
            this, factory).get(BookingRoomViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bookingRoomListener = this
        pbBookingRoom.hide()
        detailRoom()
        date()
        timeStart()
        timeEnd()
    }

    override fun onStarted() {
        pbBookingRoom.show()
    }

    override fun onSucces(message: String) {
        pbBookingRoom.hide()
        root_layoutBookingRoom.snackbar(message)
    }

    override fun onFailure(message: String) {
        pbBookingRoom.hide()
        root_layoutBookingRoom.snackbar(message)
    }

    fun date() {
        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date

                return if (isSelected)
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        else -> R.layout.item_selected_calender }
                else
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        Calendar.SATURDAY -> R.layout.item_calender_special
                        Calendar.SUNDAY -> R.layout.item_calender_special
                        else -> R.layout.item_calender
                    }
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)
                //holder.itemView.tv_month_calendar_item.text = DateUtils.getMonth3LettersName(date)
            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object : CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            @SuppressLint("SetTextI18n")
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                tvDate.text = "${DateUtils.getDayName(date)} ${DateUtils.getDayNumber(date)} ${DateUtils.getMonthName(date)} ${DateUtils.getYear(date)}"
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                //in this example sunday and saturday can't be selected, other item can be selected
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    Calendar.SATURDAY -> false
                    Calendar.SUNDAY -> false
                    else -> true
                }
            }
        }

        // here we init our calendar, also you can set more properties if you need them
        val singleRowCalendar = main_single_row_calendar.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            futureDaysCount = 10
            includeCurrentDate = true
            init()
        }
    }

    fun timeStart() {
        btnTimeStart.setOnClickListener {
            // Custom text and color
            SnapTimePickerDialog.Builder().apply {
                setTitle(R.string.titleStart)
                setPrefix(R.string.time_prefix)
                setSuffix(R.string.time_suffix)
                setThemeColor(R.color.grey)
                setTitleColor(R.color.purple)

                val start = TimeValue(7, 0)
                val end = TimeValue(16, 0)
                setSelectableTimeRange(TimeRange(start, end))

            }.build().apply {
                setListener { hour, minute -> onTimePickedStart(hour, minute) }
            }.show(activity?.supportFragmentManager!!, SnapTimePickerDialog.TAG)
        }
    }

    fun timeEnd() {
        btnTimeEnd.setOnClickListener {
            // Custom text and color
            SnapTimePickerDialog.Builder().apply {
                setTitle(R.string.titleEnd)
                setPrefix(R.string.time_prefix)
                setSuffix(R.string.time_suffix)
                setThemeColor(R.color.grey)
                setTitleColor(R.color.purple)

                val start = TimeValue(8, 0)
                val end = TimeValue(17, 0)
                setSelectableTimeRange(TimeRange(start, end))

            }.build().apply {
                setListener { hour, minute -> onTimePickedEnd(hour, minute) }
            }.show(activity?.supportFragmentManager!!, SnapTimePickerDialog.TAG)
        }
    }

    private fun onTimePickedStart(
        selectedHour: Int, selectedMinute: Int) {
        val hour = selectedHour.toString().padStart(2, '0')
        val minute = selectedMinute.toString().padStart(2, '0')
        tvTimeStart.text = String.format(getString(R.string.selected_time_format, hour, minute))
    }

    private fun onTimePickedEnd(
        selectedHour: Int, selectedMinute: Int) {
        val hour = selectedHour.toString().padStart(2, '0')
        val minute = selectedMinute.toString().padStart(2, '0')
        tvTimeEnd.text = String.format(getString(R.string.selected_time_format, hour, minute))
    }

    private fun detailRoom() {
        viewModel.detailRoom.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val imageRoom = it.image
            Glide.with(this).load(BASE +imageRoom).into(imageView3)
        })
    }

}
