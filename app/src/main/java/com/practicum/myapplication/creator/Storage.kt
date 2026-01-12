package com.practicum.myapplication.creator

import com.practicum.myapplication.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000 // 2:38
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000 // 4:43
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000 // 5:12
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000
        ),
        TrackDto(
            trackName = "Выхода нет",
            artistName = "Сплин",
            trackTimeMillis = 228000 // ~3:48
        ),
        TrackDto(
            trackName = "Моё сердце",
            artistName = "Сплин",
            trackTimeMillis = 250000 // ~4:10
        ),

        TrackDto(
            trackName = "Лирика",
            artistName = "Сектор Газа",
            trackTimeMillis = 260000 // ~4:20
        ),
        TrackDto(
            trackName = "Туман",
            artistName = "Сектор Газа",
            trackTimeMillis = 240000 // ~4:00
        ),

        // kizaru (без жести)
        TrackDto(
            trackName = "Keep Straight",
            artistName = "kizaru",
            trackTimeMillis = 170000 // ~2:50
        ),
        TrackDto(
            trackName = "Никто не нужен",
            artistName = "kizaru",
            trackTimeMillis = 190000 // ~3:10
        ),


        TrackDto(
            trackName = "ОЙДА",
            artistName = "Слава КПСС",
            trackTimeMillis = 180000 // ~3:00
        )
    )

    fun search(request: String): List<TrackDto> {
        val result = listTracks.filter {
            it.trackName.lowercase().contains(request.lowercase()) ||
                    it.artistName.lowercase().contains(request.lowercase())
        }
        return result
    }

}
