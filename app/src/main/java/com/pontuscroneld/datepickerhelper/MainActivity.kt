package com.pontuscroneld.datepickerhelper

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var startDay = 0
    var startMonth = 0
    var startYear = 0

    var day = 0
    var month = 0
    var year = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dateButton = findViewById<Button>(R.id.pickdate)

        dateButton.setOnClickListener {
            getTodayCalender()
            var startDP = DatePickerDialog(this)
            startDP.setOnDateSetListener { view, setYear, setMonth, setDayOfMonth ->

                startYear = setYear
                startMonth = setMonth
                startDay = setDayOfMonth

                var cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, startYear)
                cal.set(Calendar.MONTH, startMonth)
                cal.set(Calendar.DAY_OF_MONTH, startDay)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
                var startTime = cal.timeInMillis

                calculateDates(startTime)
            }
            startDP.show()
        }
    }


    override fun onDateSet(view: DatePicker?, setYear: Int, setMonth: Int, setDayOfMonth: Int) {
        //Den här funktionen behöver bara finnas med. setOnDateSetListener sköter logiken egentligen
    }

    fun getTodayCalender(){

        var cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    fun calculateDates(chosenDate : Long){

        var startDate = makeDateIntoNumbers(chosenDate)
        var cal = Calendar.getInstance()

        //86400000 är en dag i millisekunder
        var firstDate = addTime(21, chosenDate)
        var secondDate = addTime(65, chosenDate)
        var thirddate = addTime(149, chosenDate)
        var fourthdate = addTime(163, chosenDate)

        findViewById<TextView>(R.id.dateTV).text = "Start: " + startDate
        findViewById<TextView>(R.id.date2).text = "21 dagar: " + makeDateIntoNumbers(firstDate)
        findViewById<TextView>(R.id.date3).text = "65 dagar: " + makeDateIntoNumbers(secondDate)
        findViewById<TextView>(R.id.date4).text = "65 dagar + 12 veckor: " + makeDateIntoNumbers(thirddate)
        findViewById<TextView>(R.id.date5).text = "65 dagar + 14 veckor: " + makeDateIntoNumbers(fourthdate)
        // OSV!
    }

    fun addTime(days : Int, startDate : Long): Long{

        var cal = Calendar.getInstance()
        cal.timeInMillis = startDate
        cal.add(Calendar.DATE, days);

        var newDate = cal.timeInMillis
        return newDate

    }

    fun makeDateIntoNumbers(dateInLong : Long) : String{

        var cal = Calendar.getInstance()
        cal.timeInMillis = dateInLong

        var yearNumber = cal.get(Calendar.YEAR)
        var monthNumber = cal.get(Calendar.MONTH)
        var dayNumber = cal.get(Calendar.DAY_OF_MONTH)

        var dateString = readableDates(yearNumber, monthNumber, dayNumber)

        return dateString

    }

    fun readableDates(sYear: Int, sMon: Int, sDay: Int): String {

            var sMonString = ""
            var sDayString = ""


            if (sMon < 10) {
                sMonString = "0" + sMon.toString()
            } else {
                sMonString = sMon.toString()
            }

            if (sDay< 10) {
                sDayString = "0" + sDay.toString()
            } else {
                sDayString = sDay.toString()
            }

            var readableText = sDayString + "/" + sMonString + " " + sYear
            return readableText

    }
}