package com.example.joblux.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.joblux.ApiInterface
import com.example.joblux.R
import com.example.joblux.databinding.ActivityMainBinding
import com.example.joblux.domain.models.JobSearch
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var jobsRecyclerListAdapter: JobsRecyclerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listOfJobs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = jobsRecyclerListAdapter
        }

        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, R.layout.country_item, countries)
        val autoCmplt = (binding.country.editText as AutoCompleteTextView)
        autoCmplt.setText(countries[0])
        autoCmplt.setAdapter(adapter)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.adzuna.com/v1/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        var what = ""
        binding.textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                what = p0.toString()
                apiInterface.getJobSearch(
                    autoCmplt.text.toString(),
                    "3e97869a",
                    "0ed81a24e06fb279b7569679469b8a32",
                    20,
                    what
                ).enqueue(object : Callback<JobSearch> {
                    override fun onResponse(call: Call<JobSearch>, response: Response<JobSearch>) {
                        val jobSearch = response.body()
                        if (jobSearch != null) {
                            jobsRecyclerListAdapter.setJobList(jobSearch.results)
                        }
                    }

                    override fun onFailure(call: Call<JobSearch>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        autoCmplt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                apiInterface.getJobSearch(
                    p0.toString(), "3e97869a",
                    "0ed81a24e06fb279b7569679469b8a32",
                    20,
                    what
                ).enqueue(object : Callback<JobSearch>{
                    override fun onResponse(call: Call<JobSearch>, response: Response<JobSearch>) {
                        val jobSearch = response.body()
                        if (jobSearch != null) {
                            jobsRecyclerListAdapter.setJobList(jobSearch.results)
                        }
                    }

                    override fun onFailure(call: Call<JobSearch>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }
}