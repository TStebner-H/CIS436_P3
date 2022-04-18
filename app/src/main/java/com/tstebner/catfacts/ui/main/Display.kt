package com.tstebner.catfacts.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
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
        var description = "${content.get("description").toString()}\nOrigin: ${content.get("origin").toString()}\nLife span: ${content.get("life_span").toString()} years\nWeight: ${content.getJSONObject("weight").get("imperial").toString()} Lbs\nAffection Level: ${content.get("affection_level").toString()}"
        description += if (content.getInt("hypoallergenic") == 0) {
            "\nHypoallergenic: No"
        } else {
            "\nHypoallergenic: Yes"
        }
        description += "\nWikipedia URL:\n${content.get("wikipedia_url").toString()}"
        binding.textView.text = description

        val imgJSON = content.getJSONObject("image")
        val queue = Volley.newRequestQueue(activity?.applicationContext)
        val imageRequest = ImageRequest(
            imgJSON.getString("url"),
            {bitmap ->
                binding.imageView.setImageBitmap(bitmap)
            },
            imgJSON.getInt("width"),
            imgJSON.getInt("height"),
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            { error ->
                Log.d("DisplayFragment",error.message.toString())
            }
        )
        queue.add(imageRequest)
    }
}