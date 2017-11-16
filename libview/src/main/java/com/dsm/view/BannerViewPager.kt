package com.dsm.view

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.util.*

/**
 * BannerViewPager
 *
 * @author SJL
 * @date 2017/6/26
 */
class BannerViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {
    companion object {
        val TAG: String = "BannerViewPager"
    }

    constructor(context: Context?) : this(context, null) {}

    init {
        initView()
    }

    inner class RollHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (isRolling) {
                currentItem += 1
                adapter.notifyDataSetChanged()
            }
            sendEmptyMessageDelayed(0, rollTime)
        }
    }

    var rollTime: Long = 5000
    var isRolling: Boolean = false
    var list: MutableList<ImageView> = ArrayList()
    set(value) {
        field = value
        if(adapter!=null) {
            adapter.notifyDataSetChanged()
        }
    }
    private fun initView() {
        rollTime = 5000
        isRolling = false
        list = ArrayList()
        adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return if (list.size == 0) 0 else Int.MAX_VALUE
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                var itemPosition = position % (if (list.size == 0) 1 else list.size)
                if (list[itemPosition].parent != null) {
                    container?.removeView(list[itemPosition])
                }
                container?.addView(list[itemPosition])
                return list[itemPosition]
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
//                super.destroyItem(container, position, `object`)
            }
        }
        currentItem = Int.MAX_VALUE / 2
        adapter.notifyDataSetChanged()
        RollHandler().sendEmptyMessageDelayed(0, rollTime)
    }

}