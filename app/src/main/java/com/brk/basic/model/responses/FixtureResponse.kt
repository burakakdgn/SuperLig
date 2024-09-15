package com.brk.basic.model.responses

data class FixtureResponse(
    val get: String,
    val parameters: FixtureParameters,
    val errors: Any,  // Bu alanı Any olarak tanımlıyoruz
    val results: Int,
    val paging: Paging,
    val response: List<FixtureDetail>
)

data class FixtureParameters(
    val league: String,
    val from: String,
    val to: String,
    val season: String
)

data class Paging(
    val current: Int,
    val total: Int
)

data class FixtureDetail(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)

data class Fixture(
    val id: Int,
    val referee: String,
    val timezone: String,
    val date: String,
    val timestamp: Long,
    val periods: Periods,
    val venue: Venue,
    val status: Status
)

data class Periods(
    val first: Long?,
    val second: Long?
)

data class Venue(
    val id: Int?,
    val name: String,
    val city: String
)

data class Status(
    val long: String,
    val short: String,
    val elapsed: Int
)

data class League(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int,
    val round: String
)

data class Teams(
    val home: Team,
    val away: Team
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?
)

data class Goals(
    val home: Int?,
    val away: Int?
)

data class Score(
    val halftime: ScoreDetail,
    val fulltime: ScoreDetail,
    val extratime: ScoreDetail?,
    val penalty: ScoreDetail?
)

data class ScoreDetail(
    val home: Int?,
    val away: Int?
)
