package marekh.test.rickmortyjsonapp

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemTouch(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClick):
        RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecyclerClick {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    val GestureDetector = GestureDetectorCompat(context, object: GestureDetector.SimpleOnGestureListener(){

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            val childview = recyclerView.findChildViewUnder(e.x, e.y)
            listener.onItemClick(childview!!, recyclerView.getChildAdapterPosition(childview))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val childview = recyclerView.findChildViewUnder(e.x, e.y)
            listener.onItemLongClick(childview!!, recyclerView.getChildAdapterPosition(childview))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        try {
            GestureDetector.onTouchEvent(e)
        } catch (E: Exception) {

        }
        return super.onInterceptTouchEvent(rv, e)
    }
}