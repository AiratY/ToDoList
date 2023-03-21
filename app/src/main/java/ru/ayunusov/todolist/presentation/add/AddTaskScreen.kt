package ru.ayunusov.todolist.presentation.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

// Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content
// in the above function
@Composable
fun StartDatePicker(
    isVisible: Boolean,
    year: Int,
    month: Int,
    day: Int,
    onDismiss: () -> Unit,
    onChange: (Int, Int, Int) -> Unit

) {
    val mContext = LocalContext.current

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onChange(mYear, mMonth, mDayOfMonth)
        }, year, month, day
    )
    mDatePickerDialog.setOnDismissListener { onDismiss() }

    if (isVisible) {
        mDatePickerDialog.show()
    }
}

@Composable
fun StartTimePicker(
    isVisible: Boolean,
    hour: Int,
    minute: Int,
    onChange: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val mContext = LocalContext.current
    val timePickerDialog = TimePickerDialog(mContext, { _, mHour, mMinute ->
        onChange(mHour, mMinute)
    }, hour, minute, true)

    timePickerDialog.setOnDismissListener { onDismiss() }

    if (isVisible) {
        timePickerDialog.show()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //DatePicker()
}

