package com.example.dialog_fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

// В этом коде много констант, это сдалано для простоты
// так как весь фрагмент представляет собой единственный независимый файл

class TestFragment : Fragment() {

    private lateinit var mView: TestView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = TestView(activity)
        return mView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView.setOnClickListener {
            TestCheckboxDialog(id).show(requireActivity().supportFragmentManager, "YesNoCancelDialog")
        }
        mView.setState(1,2, sun = false, moonstars = true)
    }

    /*******************************************
    Функция установки времени суток
     ********************************************/

    public fun setState(hour:Int, minute: Int, sun: Boolean, moonstars: Boolean){
        mView.setState(hour, minute, sun, moonstars)
    }



    private class TestView(context: Context?) : View(context) {

        private var hour: Int = 12
        private var minute: Int = 0
        private var moonstars = true
        private var sun = true
        private var skyColor = Color.BLUE
        private var groundColor = Color.GREEN
        private var isDay = true

        init {
            val c = Calendar.getInstance();
            // Current Hour
            hour = c.get(Calendar.HOUR_OF_DAY);
            // Current Minute
            minute = c.get(Calendar.MINUTE);
            setState(hour, minute, sun, moonstars)
        }

        val p = Paint()
        val rnd = Random()

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawColor(skyColor)
            p.setColor(Color.YELLOW)
            if (sun && isDay) {
                canvas.drawCircle(canvas.width / 2f, 200f, 40f, p)
            }
            if (moonstars && !isDay) {
                for (i in 0..100) {
                    canvas.drawCircle(
                        rnd.nextInt(canvas.width).toFloat(),
                        rnd.nextInt(canvas.height).toFloat(),
                        2f,
                        p
                    )
                }
                p.setColor(Color.rgb(244, 241, 201))
                canvas.drawCircle(canvas.width / 4f * 3, 300f, 40f, p)

            }
            p.setColor(groundColor)
            canvas.drawRect(0f, canvas.height / 10f * 9,
                canvas.width.toFloat(), canvas.height.toFloat(), p)
        }

        fun setState(hour: Int, minute: Int, sun: Boolean, moonstars: Boolean) {
            this.sun = sun
            this.moonstars = moonstars
            this.hour = hour
            this.minute = minute
            val dayTimeHour = hour + minute / 60f
            val dark = if (dayTimeHour < 3 || dayTimeHour > 21) {
                1f
            } else {
                Math.abs(dayTimeHour - 12) / 9
            }
            val light = 1 - dark
            val red = (0xff * light + 0x00 * dark).toInt()
            val green = (0xff * light + 0x18 * dark).toInt()
            val blue = (0xff * light + 0x3e * dark).toInt()
            isDay = dayTimeHour > 6 && dayTimeHour <= 18
            skyColor = Color.rgb(red, green, blue)
            groundColor = Color.HSVToColor(floatArrayOf(120f, 1f, dark))
            invalidate()
        }
    }
}
