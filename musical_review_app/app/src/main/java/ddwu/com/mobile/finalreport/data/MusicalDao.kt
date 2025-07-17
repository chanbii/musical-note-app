package ddwu.com.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log

class MusicalDao(context: Context) {
    val helper : MusicalDBHelper

    init{
        helper = MusicalDBHelper(context)
    }


    @SuppressLint("Range")
    fun getAllMusicals() : List<MusicalDto>{
        val db = helper.readableDatabase
        val cursor = db.query(MusicalDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val musicals = arrayListOf<MusicalDto>()
        with(cursor){
            while(moveToNext()){
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val image = getInt(getColumnIndex(MusicalDBHelper.COL_IMAGE))
                val musical = getString(getColumnIndex(MusicalDBHelper.COL_MUSICAL))
                val score = getDouble(getColumnIndex(MusicalDBHelper.COL_SCORE))
                val date = getString(getColumnIndex(MusicalDBHelper.COL_DATE))
                val grade = getString(getColumnIndex(MusicalDBHelper.COL_GRADE))
                val price = getInt(getColumnIndex(MusicalDBHelper.COL_PRICE))
                val actor = getString(getColumnIndex(MusicalDBHelper.COL_ACTOR))
                val theater = getString(getColumnIndex(MusicalDBHelper.COL_THEATER))
                val production = getString(getColumnIndex(MusicalDBHelper.COL_PRODUCTION))
                val review = getString(getColumnIndex(MusicalDBHelper.COL_REVIEW))
                val dto = MusicalDto(id, image, musical, score, date, grade, price, actor, theater, production, review)
                musicals.add(dto)
            }
        }
        cursor.close()
        helper.close()
        return musicals
    }

    fun addMusical(musicalDto: MusicalDto) : Long {
        val db = helper.writableDatabase
        val newMusical = ContentValues()
        newMusical.put(MusicalDBHelper.COL_MUSICAL, musicalDto.musical)
        newMusical.put(MusicalDBHelper.COL_IMAGE, musicalDto.image)
        newMusical.put(MusicalDBHelper.COL_SCORE, musicalDto.score)
        newMusical.put(MusicalDBHelper.COL_DATE, musicalDto.date)
        newMusical.put(MusicalDBHelper.COL_GRADE, musicalDto.grade)
        newMusical.put(MusicalDBHelper.COL_PRICE, musicalDto.price)
        newMusical.put(MusicalDBHelper.COL_ACTOR, musicalDto.actor)
        newMusical.put(MusicalDBHelper.COL_THEATER, musicalDto.theater)
        newMusical.put(MusicalDBHelper.COL_PRODUCTION, musicalDto.production)
        newMusical.put(MusicalDBHelper.COL_REVIEW, musicalDto.review)
        val result = db.insert(MusicalDBHelper.TABLE_NAME, null, newMusical)
        helper.close()
        return result
    }

    fun remove(id: Long): Long {
        val db = helper.writableDatabase
        val whereClause = BaseColumns._ID + "=?"
        val whereArgs = arrayOf(id.toString())
        val result = db.delete(MusicalDBHelper.TABLE_NAME, whereClause, whereArgs)
        helper.close()
        return result.toLong()
    }

    fun updateMusical(musicalDto: MusicalDto): Long {
        val db = helper.writableDatabase

        val updateRow = ContentValues()
        updateRow.put(MusicalDBHelper.COL_MUSICAL, musicalDto.musical)
        updateRow.put(MusicalDBHelper.COL_IMAGE, musicalDto.image)
        updateRow.put(MusicalDBHelper.COL_SCORE, musicalDto.score)
        updateRow.put(MusicalDBHelper.COL_DATE, musicalDto.date)
        updateRow.put(MusicalDBHelper.COL_GRADE, musicalDto.grade)
        updateRow.put(MusicalDBHelper.COL_PRICE, musicalDto.price)
        updateRow.put(MusicalDBHelper.COL_ACTOR, musicalDto.actor)
        updateRow.put(MusicalDBHelper.COL_THEATER, musicalDto.theater)
        updateRow.put(MusicalDBHelper.COL_PRODUCTION, musicalDto.production)
        updateRow.put(MusicalDBHelper.COL_REVIEW, musicalDto.review)
        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(musicalDto.id.toString())

        val result = db.update(MusicalDBHelper.TABLE_NAME, updateRow, whereClause, whereArgs)
        helper.close()

        return result.toLong()
    }

    fun searchByTitle(keyword: String): List<MusicalDto> {
        val db = helper.readableDatabase
        Log.d("Search", "Query: $keyword")
        val cursor = db.rawQuery(
            "SELECT * FROM ${MusicalDBHelper.TABLE_NAME} WHERE ${MusicalDBHelper.COL_MUSICAL} LIKE ?",
            arrayOf("%$keyword%")
        )

        val result = mutableListOf<MusicalDto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val image = getInt(getColumnIndexOrThrow(MusicalDBHelper.COL_IMAGE))
                val musical = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_MUSICAL))
                val score = getDouble(getColumnIndexOrThrow(MusicalDBHelper.COL_SCORE))
                val date = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_DATE))
                val grade = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_GRADE))
                val price = getInt(getColumnIndexOrThrow(MusicalDBHelper.COL_PRICE))
                val actor = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_ACTOR))
                val theater = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_THEATER))
                val production = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_PRODUCTION))
                val review = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_REVIEW))

                val dto = MusicalDto(id, image, musical, score, date, grade, price, actor, theater, production, review)
                result.add(dto)
            }
        }
        cursor.close()
        helper.close()
        return result
    }

    fun sortByScore(): List<MusicalDto>{
        val db = helper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${MusicalDBHelper.TABLE_NAME} ORDER BY ${MusicalDBHelper.COL_SCORE} DESC",
            null
        )

        val result = mutableListOf<MusicalDto>()
        with(cursor){
            while(moveToNext()){
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val image = getInt(getColumnIndexOrThrow(MusicalDBHelper.COL_IMAGE))
                val musical = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_MUSICAL))
                val score = getDouble(getColumnIndexOrThrow(MusicalDBHelper.COL_SCORE))
                val date = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_DATE))
                val grade = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_GRADE))
                val price = getInt(getColumnIndexOrThrow(MusicalDBHelper.COL_PRICE))
                val actor = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_ACTOR))
                val theater = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_THEATER))
                val production = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_PRODUCTION))
                val review = getString(getColumnIndexOrThrow(MusicalDBHelper.COL_REVIEW))

                val dto = MusicalDto(id, image, musical, score, date, grade, price, actor, theater, production, review)
                result.add(dto)
            }
            cursor.close()
            helper.close()
            return result
        }
    }
}