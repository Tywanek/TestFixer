package com.radlab.testfixer.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlab.testfixer.data.FixerResponse
import com.radlab.testfixer.data.Rate
import com.radlab.testfixer.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class FixerViewModel : ViewModel() {

    private var responseDate: LocalDate? = null
    private var adapterItems: MutableLiveData<MutableList<Rate>>? = null
    private val retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val adapterList: LiveData<MutableList<Rate>>
        get() {
            if (adapterItems == null) {
                adapterItems = MutableLiveData()
                getResponse()
            }
            return adapterItems as MutableLiveData<MutableList<Rate>>
        }

    private fun getResponse() {

        val api = retrofit.create(Api::class.java)
        val call = api.getResponse
        call.enqueue(object : Callback<FixerResponse> {
            override fun onFailure(call: Call<FixerResponse>, t: Throwable) {}

            override fun onResponse(call: Call<FixerResponse>, response: Response<FixerResponse>) {
                adapterItems?.value = createAdapterDataSet(response.body()!!)
            }
        })
    }

    fun loadMore() {

        val api = retrofit.create(Api::class.java)
        val call = api.getMoreResponse(getDate())

        call.enqueue(object : Callback<FixerResponse> {
            override fun onFailure(call: Call<FixerResponse>, t: Throwable) {}

            override fun onResponse(call: Call<FixerResponse>, response: Response<FixerResponse>) {

                createAdapterDataSet(response.body()!!).forEach {
                    adapterItems?.value?.add(it)
                }
            }
        })
    }


    @SuppressLint("NewApi")
    private fun getDate(): String {

        if (responseDate == null) {
            responseDate = LocalDate.now().minusDays(1)
        } else {
            responseDate = responseDate!!.minusDays(1)
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return responseDate!!.format(formatter)
    }

    private fun createAdapterDataSet(response: FixerResponse): MutableList<Rate> {

        val result: MutableList<Rate> = mutableListOf<Rate>()
        result.add(Rate(response.date, null, "", ""))

        response.rates.javaClass.declaredFields.iterator().forEach {
            val field = response.rates.javaClass.getDeclaredField(it.name)
            field.isAccessible = true

            val value = field.getDouble(response.rates)
            result.add(Rate(null, response.date, it.name, value.toString()))
        }
        return result
    }
}