package com.rizkifajar.airis.protocols

interface UIUpdater {
    fun resetUIWithConnection(status: Boolean)
    fun updateStatusViewWith(status: String)
    fun update(message: String)
}