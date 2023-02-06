package com.example.micarpetadeyugioh

data class Deck (
    var deckPrincipal: MutableList<Carta>,
    var deckExtra: MutableList<Carta>,
    var deckSoporte: MutableList<Carta>,
        )