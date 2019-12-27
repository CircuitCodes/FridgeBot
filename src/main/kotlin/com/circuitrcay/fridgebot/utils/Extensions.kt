package com.circuitrcay.fridgebot.utils

fun String.sanitiseFromIrc(): String {
    return this.replace("@", "@\u200b")
        .replace("#", "#\u200b")
}

fun String.matrixReplace(): String {
    return this.replace("[m]", " <:matrix:658096348851339275>")
}