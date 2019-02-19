package com.tml.libs.cutils

interface ListDataProvider {
    fun size():Int
    fun itemAt(index: Int): Any?
}

class EmptyListDataProvider : ListDataProvider {
    override fun itemAt(index: Int): Any? {
        return null
    }

    override fun size(): Int {
        return 0
    }

}