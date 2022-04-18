package com.tstebner.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import android.widget.ArrayAdapter
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.tstebner.catfacts.ui.main.Display
import com.tstebner.catfacts.ui.main.Spinner

class MainActivity : AppCompatActivity(), Spinner.SpinnerListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        apiCall()
    }

    override fun onBreedSelect(content: JSONObject) {
        val display = supportFragmentManager.findFragmentById(R.id.displayFragment) as Display
        display.updateDisplay(content)
    }
}