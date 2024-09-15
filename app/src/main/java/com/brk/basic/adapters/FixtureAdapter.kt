package com.brk.basic.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brk.basic.R
import com.brk.basic.model.responses.FixtureDetail
import com.brk.basic.view.DetayActivity
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class FixtureAdapter(private val fixtureList: List<FixtureDetail>) :
    RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fixture, parent, false)
        return FixtureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        val fixture = fixtureList[position]
        holder.homeTeamName.text = fixture.teams.home.name
        holder.awayTeamName.text = fixture.teams.away.name

        // Skorları kontrol et ve gerekli formatı uygula
        val homeGoals = fixture.goals.home?.toString() ?: "-"
        val awayGoals = fixture.goals.away?.toString() ?: "-"
        holder.matchScore.text = "$homeGoals - $awayGoals"

        // Maç saatini ayarla
        val matchDate = fixture.fixture.date
        val formattedDate = formatDate(matchDate)
        holder.matchTime.text = formattedDate


        // Get the city name
        val cityName = fixture.fixture.venue.city ?: "Unknown City"

        // Tıklama olayını ayarla
        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, DetayActivity::class.java).apply {
                putExtra("HOME_TEAM_NAME", fixture.teams.home.name)
                putExtra("AWAY_TEAM_NAME", fixture.teams.away.name)
                putExtra("MATCH_DATE", matchDate)
                putExtra("MATCH_SCORE", "${homeGoals} - ${awayGoals}") // Skor bilgisini ekle
                putExtra("REFEREE", fixture.fixture.referee)
                putExtra("VENUE_NAME", fixture.fixture.venue.name)
                putExtra("CITY_NAME", cityName)  // Added city name
            }
            context.startActivity(intent)
        }
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
    override fun getItemCount(): Int = fixtureList.size

    inner class FixtureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchTime: TextView = itemView.findViewById(R.id.matchTime)
        val homeTeamName: TextView = itemView.findViewById(R.id.homeTeamName)
        val awayTeamName: TextView = itemView.findViewById(R.id.awayTeamName)
        val matchScore: TextView = itemView.findViewById(R.id.matchScore)

    }
}