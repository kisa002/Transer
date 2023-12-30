package com.haeyum.shared.extensions

fun String.decodeHtmlEntities() =
    replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&").replace("&quot;", "\"")
        .replace("&apos;", "'").replace("&#39;", "'")