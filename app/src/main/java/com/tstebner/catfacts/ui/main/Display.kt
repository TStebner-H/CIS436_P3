package com.tstebner.catfacts.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tstebner.catfacts.databinding.FragmentDisplayBinding
import org.json.JSONObject

class Display : Fragment() {
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = Display()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun updateDisplay(content: JSONObject) {
        var description = "${content.get("description").toString()}\nOrigin: ${content.get("origin").toString()}\nLife span: ${content.get("life_span").toString()} years\nWeight: ${content.getJSONObject("weight").get("imperial").toString()}\nAffection Level: ${content.get("affection_level").toString()}"
        description += if (content.getInt("hypoallergenic") == 0) {
            "\nHypoallergenic: No"
        } else {
            "\nHypoallergenic: Yes"
        }
        description += "\nWikipedia URL:\n${content.get("wikipedia_url").toString()}"
        binding.textView.text = description

        val imgJSON = content.getJSONObject("image")
    }
}