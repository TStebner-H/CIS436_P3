package com.tstebner.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import android.widget.ArrayAdapter
import com.tstebner.catfacts.ui.main.Display
import com.tstebner.catfacts.ui.main.Spinner

class MainActivity : AppCompatActivity(), Spinner.SpinnerListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBreedSelect(content: JSONObject) {
        val display = supportFragmentManager.findFragmentById(R.id.displayFragment) as Display
        display.updateDisplay(content)
    }
}