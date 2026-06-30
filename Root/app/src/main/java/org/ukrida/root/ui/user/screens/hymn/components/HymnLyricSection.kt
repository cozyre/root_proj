package org.ukrida.root.ui.user.screens.hymn.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Song

@Composable
fun HymnLyricsSection(
    song: Song
) {
    val sections = parseLyrics(song.lyrics)
    Column {
        sections.forEach { section ->
            LyricBlock(
                title = section.first,
                lyrics = section.second
            )
            Spacer(modifier = androidx.compose.ui.Modifier.height(32.dp))
        }
    }
}
// TODO Backend Integration
// If backend later returns structured lyrics
// (Verse, Chorus, Bridge as separate objects),
// this parser can be removed and LyricBlock
// can directly consume the API response.
private fun parseLyrics(
    lyrics: String?
): List<Pair<String, String>> {
    if (lyrics.isNullOrBlank()) return emptyList()
    val result = mutableListOf<Pair<String, String>>()
    val regex = Regex(
        "(Verse\\s*\\d+|Chorus|Bridge|Ending|Intro)",
        RegexOption.IGNORE_CASE
    )
    val matches = regex.findAll(lyrics).toList()
    if (matches.isEmpty()) {
        result.add(
            "Lyrics" to lyrics
        )
        return result
    }
    for (i in matches.indices) {
        val title = matches[i].value
        val start = matches[i].range.last + 1
        val end =
            if (i < matches.lastIndex)
                matches[i + 1].range.first
            else
                lyrics.length
        val content =
            lyrics.substring(start, end).trim()
        result.add(
            title to content
        )
    }
    return result
}