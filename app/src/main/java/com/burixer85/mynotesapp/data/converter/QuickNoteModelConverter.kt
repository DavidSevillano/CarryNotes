package com.burixer85.mynotesapp.data.converter

import androidx.room.TypeConverter
import com.burixer85.mynotesapp.data.entity.QuickNoteModel
import com.google.gson.Gson

class QuickNoteModelConverter {
    @TypeConverter
    fun fromQuickNoteModel(quickNote: QuickNoteModel): String {
        return Gson().toJson(quickNote)
    }

    @TypeConverter
    fun toQuickNoteModel(json: String): QuickNoteModel {
        return Gson().fromJson(json, QuickNoteModel::class.java)
    }
}