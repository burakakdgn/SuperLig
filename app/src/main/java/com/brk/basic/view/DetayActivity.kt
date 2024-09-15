package com.brk.basic.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brk.basic.R
import com.brk.basic.databinding.ActivityDetayBinding
import com.brk.basic.databinding.ActivityMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class DetayActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeTeamName = intent.getStringExtra("HOME_TEAM_NAME")
        val awayTeamName = intent.getStringExtra("AWAY_TEAM_NAME")
        val matchDate = intent.getStringExtra("MATCH_DATE")
        val matchScore = intent.getStringExtra("MATCH_SCORE")
        val referee = intent.getStringExtra("REFEREE")
        val venueName = intent.getStringExtra("VENUE_NAME")
        val cityName = intent.getStringExtra("CITY_NAME")


        binding.homeTeamName.text = homeTeamName
        binding.awayTeamName.text = awayTeamName
        binding.matchDate.text = formatDate(matchDate!!)
        binding.matchScore.text = matchScore
        binding.refereeTextView.text = referee
        binding.venueTextView.text = venueName
        binding.city.text = cityName
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

}