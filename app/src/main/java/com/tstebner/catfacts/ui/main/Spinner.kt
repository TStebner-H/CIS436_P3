package com.tstebner.catfacts.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.tstebner.catfacts.databinding.FragmentSpinnerBinding
import org.json.JSONObject
import java.lang.ClassCastException
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Spinner : Fragment() {

    private lateinit var binding: FragmentSpinnerBinding
    private lateinit var viewModel: MainViewModel
    private var activityCallback : Spinner.SpinnerListener? = null

    private var breedArr = arrayListOf<String>("Choose Breed:")

    interface SpinnerListener {
        fun onBreedSelect(content: JSONObject)
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)

        try {
            activityCallback = context as SpinnerListener
        }
        catch (e : ClassCastException){
            throw ClassCastException("$context must implement SpinnerListener")
        }
    } // end onAttach

    companion object {
        fun newInstance() = Spinner()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    } // end onCreate function

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinnerBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setAdapter()
        binding.spinnerBreed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, poistion: Int, id: Long) {

            }
        }
    }

    fun setAdapter() {
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, breedArr) }

        binding.spinnerBreed.adapter = arrayAdapter
    }

    private fun breedSelect(content: JSONObject) {
        activityCallback?.onBreedSelect(content)
    }
}