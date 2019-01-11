package com.radlab.testfixer

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.radlab.testfixer.adapters.RatesAdapter
import com.radlab.testfixer.data.Rate
import com.radlab.testfixer.viewModels.FixerViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RatesAdapter.OnRateClickListener {

    override fun onRateClick(item: Rate) {
        Toast.makeText(this@MainActivity, item.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = ViewModelProviders.of(this).get(FixerViewModel::class.java)

        val scrollListener = object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_SETTLING) {
                    model.loadMore()
                }
            }
        }

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.addOnScrollListener(scrollListener)

        model.adapterList.observe(this, Observer<MutableList<Rate>> {
            recyclerview.adapter = RatesAdapter(it, this)
        })

    }
}