package com.radlab.testfixer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.radlab.testfixer.data.Rate
import com.radlab.testfixer.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val item = intent.extras.get(resources.getString(R.string.EXTRA_DETAIL_ITEM)) as Rate

        binding.setVariable(BR.item,item)
    }
}