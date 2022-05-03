package com.example.dialog_fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

class TestTimePickerDialog(private val params: Array<Boolean>, val id1: Int,val fmm : FragmentManager): DialogFragment() {
    val c = Calendar.getInstance();
    val curHour = c.get(Calendar.HOUR_OF_DAY);
    val curMinute = c.get(Calendar.MINUTE);
    val a:Boolean = params[0]
    val b:Boolean = params[1]
    var fm: FragmentManager = fmm
    val fragment: TestFragment = fm.findFragmentById(id1) as TestFragment;
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("ggs", a.toString() + b)
        return TimePickerDialog(activity, {
                _, hour, minute ->
            fragment.setState(
                hour,
                minute,
                a,
                b,
            )
        }, curHour, curMinute, false)
    }
}