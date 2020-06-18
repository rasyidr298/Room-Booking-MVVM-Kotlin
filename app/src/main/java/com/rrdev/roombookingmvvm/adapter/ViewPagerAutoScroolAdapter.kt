package com.rrdev.roombookingmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.rrdev.roombookingmvvm.R

class ViewPagerAutoScroolAdapter (
    context: Context,
    itemList: ArrayList<Int>,
    isInfinite: Boolean
    ) : LoopingPagerAdapter<Int>(context, itemList, isInfinite) {

        override fun getItemViewType(listPosition: Int): Int {
            return if (itemList?.get(listPosition) == 0) VIEW_TYPE_SPECIAL else VIEW_TYPE_NORMAL
        }

        override fun inflateView(
            viewType: Int,
            container: ViewGroup,
            listPosition: Int
        ): View {
            return if (viewType == VIEW_TYPE_SPECIAL) LayoutInflater.from(
                context
            ).inflate(R.layout.item_auto_viewpager_special, container, false)
            else LayoutInflater.from(context).inflate(R.layout.item_auto_viewpager, container, false)
        }

        override fun bindView(
            convertView: View,
            listPosition: Int,
            viewType: Int
        ) {
            if (viewType == VIEW_TYPE_SPECIAL) return
            convertView.findViewById<View>(R.id.image)
               .setBackgroundResource((getBackgroundColor(listPosition)))
//            val description = convertView.findViewById<TextView>(R.id.description)
//            description.text = itemList?.get(listPosition).toString()
        }

        private fun getBackgroundColor(number: Int): Int {
            return when (number) {
                0 -> R.drawable.img_contoh
                1 -> R.drawable.img_contoh
                2 -> R.drawable.img_contoh
                3 -> R.drawable.img_contoh
                4 -> android.R.color.holo_purple
                5 -> android.R.color.black
                else -> android.R.color.black
            }
        }

        companion object {
            private const val VIEW_TYPE_NORMAL = 100
            private const val VIEW_TYPE_SPECIAL = 101
        }
    }