package me.monmcgt.code.helpers

const val rightArrow = "»"

const val colourKey = "§"

const val reset = colourKey + "r"

const val grey = colourKey + "7"
const val pink = colourKey + "d"
const val yellow = colourKey + "e"
const val lime = colourKey + "a"
const val dark_aqua = colourKey + "3"
const val red = colourKey + "c"
const val orange = colourKey + "6"

const val bold = colourKey + "l"
const val underline = colourKey + "n"

fun formatStats(stars: String?, name: String?, fkdr: String?, winrate: String?, winstreak: String?): String? {
    var stars = stars
    var nicked = false
    var starColour = ""
    if (stars == null) {
        stars = "???"
        nicked = true
        starColour = dark_aqua
    } else if (stars.toInt() <= 200) {
        starColour = dark_aqua
    } else {
        starColour = red
    }
    return if (nicked) {
        String.format(
            "%s[%sLA%s] %s[%s] %s%s %s%s",
            grey,
            pink,
            grey,
            yellow,
            stars,
            dark_aqua,
            name,
            yellow,
            "is nicked!"
        )
    } else {
        String.format(
            "%s[%sLA%s] %s[%s] %s%s %s- %s%s %sFKDR - %s%s %sWLR - %s%s %sWS",
            grey,
            pink,
            grey,
            yellow,
            stars,
            starColour,
            name,
            yellow,
            lime,
            fkdr,
            yellow,
            lime,
            winrate,
            yellow,
            lime,
            winstreak,
            yellow
        )
    }
}

fun addPrefix(message: String?): String {
    return String.format("%s[%sLA%s] %s", grey, pink, grey, message)
}