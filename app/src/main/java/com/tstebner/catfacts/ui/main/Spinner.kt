package com.tstebner.catfacts.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tstebner.catfacts.R
import org.json.JSONArray

class Spinner : Fragment() {

    private lateinit var binding: FragmentSpinnerBinding
    private lateinit var viewModel: MainViewModel
    private var activityCallback : Spinner.SpinnerListener? = null

    private var breedArr = arrayListOf<String>("Choose Breed:")
    private var contentArr = JSONArray()


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
        binding.spinnerBreed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("SpinnerFragment","in onNothingSelected")
            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val breed = adapterView?.getItemAtPosition(position).toString()
                Log.d("SpinnerFragment", breed)
                if (breed != "Choose Breed:" && breedArr[0] == "Choose Breed:") {
                    breedArr.removeAt(0)
                }
                for (i in 0 until contentArr.length()) {
                    val item = contentArr.getJSONObject(i)
                    if (item.get("name").toString() == breed) {
                        breedSelect(item)
                        break
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        val key = "?api_key=d9c0c183-0104-4a8f-9729-b6a39bcdb292"
//        val url = "https://jsonplaceholder.typicode.com/posts"
        val url = "https://api.thecatapi.com/v1/breeds?api_key=$key"
        val queue = Volley.newRequestQueue(activity?.applicationContext)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { respArr ->
                Log.d("SpinnerFragment", "Api call successful")
                for (i in 0 until respArr.length()) {
                    val item = respArr.getJSONObject(i)
                    contentArr.put(item)
                    breedArr.add(item.get("name").toString())
                }
            },
            {
                Log.d("SpinnerFragment", "Api call failed")
            },
        )
        queue.add(jsonArrayRequest)

        Log.d("SpinnerFragment", breedArr.toString())
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, breedArr) }

        binding.spinnerBreed.adapter = arrayAdapter
    }

    private fun breedSelect(content: JSONObject) {
        Log.d("SpinnerFragment",content.toString())
        activityCallback?.onBreedSelect(content)
    }
}