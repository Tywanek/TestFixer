package com.radlab.testfixer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.radlab.testfixer.adapters.RatesAdapter
import com.radlab.testfixer.data.Rate
import com.radlab.testfixer.viewModels.FixerViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RatesAdapter.OnRateClickListener {

    override fun onRateClick(item: Rate) {
        if(item.rateDate!=null){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(getString(R.string.EXTRA_DETAIL_ITEM), item)
            startActivity(intent)
            }
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