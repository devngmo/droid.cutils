package com.tml.libs.cutils

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class JSoupUtils {
    companion object {
        fun selectOne(parent: Element, cssQuery:String): Element? {
            val ls = parent.select(cssQuery)
            if (ls == null || ls.size == 0) return null
            return ls[0]
        }
    }
}