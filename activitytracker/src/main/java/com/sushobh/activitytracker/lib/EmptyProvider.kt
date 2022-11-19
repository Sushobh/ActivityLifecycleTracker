package com.sushobh.activitytracker.lib

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

internal abstract class EmptyProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri {
        throw Exception("unimplemented, why are you using this lol?")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        throw Exception("unimplemented, why are you using this lol?")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw Exception("unimplemented,why are you using this lol?")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw Exception("unimplemented,why are you using this lol?")
    }

    override fun getType(uri: Uri): String {
        throw Exception("unimplemented,why are you using this lol?")
    }
}


internal class AppContextProvider : EmptyProvider() {
    override fun onCreate(): Boolean {
        val ctx = context
        if (ctx is Application) {
            ActTracker.start(ctx)
        }
        return true
    }
}