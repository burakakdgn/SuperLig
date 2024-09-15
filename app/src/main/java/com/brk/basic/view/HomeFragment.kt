package com.brk.basic.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brk.basic.R
import com.brk.basic.base.ViewModelFactory
import com.brk.basic.data.api.ApiHelper
import com.brk.basic.data.api.ApiInterface
import com.brk.basic.data.api.Retrofit
import com.brk.basic.databinding.FragmentHomeBinding
import com.brk.basic.dialogs.MessageDialog
import com.brk.basic.ui.FixtureAdapter
import com.brk.basic.utils.Status
import com.brk.basic.viewmodel.GenelViewModel
import com.brk.basic.viewmodel.LoadingDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel: GenelViewModel
    private lateinit var loading : Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        init()
        funcInit()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(Retrofit.getClient().create(ApiInterface::class.java)))
        )[GenelViewModel::class.java]
        loading = LoadingDialog(requireContext(),false).getLoading()
    }
    private fun funcInit(){

        binding.textViewSelectedDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun init(){
        val today = Calendar.getInstance()
        val fromDate = dateFormat.format(today.time)
        today.add(Calendar.DAY_OF_MONTH, 1)
        val toDate = dateFormat.format(today.time)
        getFixturesDate(fromDate, toDate)
        getFixturesDate2(fromDate)


    }
    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: "-"
        } catch (e: ParseException) {
            e.printStackTrace()
            "-"
        }
    }
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }

                val fromDate = dateFormat.format(selectedDate.time)
                selectedDate.add(Calendar.DAY_OF_MONTH, 0)
                val toDate = dateFormat.format(selectedDate.time)

                Log.e("tarih","$fromDate")
                Log.e("tarih","$toDate")
                binding.textViewSelectedDate.text = "Seçilmiş Gün: $fromDate"
                binding.tarih.text = fromDate.toString()
                getFixturesDate(fromDate, toDate)
            },
            year, month, day
        )
        datePicker.show()
    }


    private fun getFixturesDate(fromDate: String, toDate: String) {
        Log.e("tarih", "$fromDate")
        Log.e("tarih", "$toDate")
        viewModel.getFixturesDate(203, 2024, fromDate, toDate).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data != null) {
                            val fixtures = resource.data.response

                            // Get the formatted date for the first fixture in the list
                            val formattedDate = if (fixtures.isNotEmpty()) {
                                formatDate(fixtures[0].fixture.date)
                            } else {
                                fromDate // Fallback to the `fromDate` if there are no fixtures
                            }

                            Log.d("FixtureInfo", "Fixture Date: $formattedDate")
                            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            binding.recyclerView.adapter = FixtureAdapter(fixtures)

                            // Set the text to display only the date
                            binding.tarih.text = formattedDate
                            binding.textViewSelectedDate.text = formattedDate
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        MessageDialog(requireContext(), false, "Hata", it.message.toString()).getDialog().show()
                        Log.d("FixtureInfo", "${it.message}")
                    }
                    Status.LOADING -> {
                        loading.show()
                    }
                }
            }
        }
    }
    private fun getFixturesDate2(fromDate: String) {
        // Parse the fromDate string to a Date object
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        try {
            val date = inputFormat.parse(fromDate)
            if (date != null) {
                calendar.time = date
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        // Add one day to the provided date
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val toDate = inputFormat.format(calendar.time)

        Log.e("tarih", "Fetching fixtures for date: $toDate")

        viewModel.getFixturesDate(203, 2024, toDate, toDate).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data != null) {
                            val fixtures = resource.data.response

                            // Set the text to display only the date
                            binding.tarih2.text = toDate
                            if(toDate != null){
                                binding.tarih2.visibility = View.VISIBLE
                                binding.tarih2.text = toDate
                            }else {
                                binding.tarih2.visibility = View.GONE
                            }

                            // Update the RecyclerView with the fixtures
                            binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
                            binding.recyclerView2.adapter = FixtureAdapter(fixtures)
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        MessageDialog(requireContext(), false, "Hata", it.message.toString()).getDialog().show()
                        Log.d("FixtureInfo", "${it.message}")
                    }
                    Status.LOADING -> {
                        loading.show()
                    }
                }
            }
        }
    }
    private fun getFixtures(){
        viewModel.getFixtures().observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data != null) {
                            // Gelen veriyi loga basma
                            resource.data.response.forEach { fixtureDetail ->
                                Log.d("FixtureInfo", "60")
                                Log.d("FixtureInfo", "${resource.data.response}")
                                val fixtures = resource.data.response
                                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                binding.recyclerView.adapter = FixtureAdapter(fixtures)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Log.d("FixtureInfo", "69")
                        loading.dismiss()
                        MessageDialog(requireContext(),false,"Hata",it.message.toString()).getDialog().show()
                        Log.d("FixtureInfo", "${it.message}")
                    }
                    Status.LOADING -> {
                        loading.show()
                    }
                }
            }
        }
    }
}