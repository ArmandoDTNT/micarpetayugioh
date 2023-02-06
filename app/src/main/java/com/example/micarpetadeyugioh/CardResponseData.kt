package com.example.micarpetadeyugioh

data class CardResponse(
    val data: List<CardResponseData>,
)

data class CardResponseData (
    val id: Int,
    val name: String,
    val type: String,
    val desc: String,
    val atk: Int,
    val def: Int,
    val level: Int,
    val race: String,
    val attribute: String,
    val card_sets: List<CardSets>,
    val card_images: List<CardImage>,
    val card_prices: List<cardPrices>
)

data class CardSets(
    val set_name: String,
    val set_code: String,
    val set_rarity: String,
    val set_rarity_code: String,
    val set_price: String,

)

data class CardImage(
    val id: Int,
    val image_url: String,
    val image_url_small: String,
)

data class cardPrices(
    val cardmarked_price: String,
    val tcgplayer_price: String,
    val ebay_price: String,
    val amazon_price: String,
    val coolstuffinc_price: String,
)

