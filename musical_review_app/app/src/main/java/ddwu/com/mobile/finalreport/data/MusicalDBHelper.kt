package ddwu.com.mobile.finalreport.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import ddwu.com.mobile.finalreport.R

class MusicalDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "DBHelper"

    companion object{
        const val DB_NAME = "musical_db"
        const val TABLE_NAME = "musical_table"
        const val COL_IMAGE = "image"
        const val COL_MUSICAL = "musical"
        const val COL_SCORE = "score"
        const val COL_DATE = "date"
        const val COL_GRADE = "grade"
        const val COL_PRICE = "price"
        const val COL_ACTOR = "actor"
        const val COL_THEATER = "theater"
        const val COL_PRODUCTION = "production"
        const val COL_REVIEW = "review"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_MUSICAL = "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_IMAGE INTEGER NOT NULL, " +
                "$COL_MUSICAL TEXT, " +
                "$COL_SCORE DOUBLE, " +
                "$COL_DATE TEXT, " +
                "$COL_GRADE TEXT, " +
                "$COL_PRICE INTEGER, " +
                "$COL_ACTOR TEXT, " +
                "$COL_THEATER TEXT, " +
                "$COL_PRODUCTION TEXT, " +
                "$COL_REVIEW TEXT" +
                ")"
        db?.execSQL(CREATE_TABLE_MUSICAL)

        // 지킬앤하이드
        var values = ContentValues().apply{
            put(COL_IMAGE, R.mipmap.jekyll)
            put(COL_MUSICAL, "지킬앤하이드")
            put(COL_SCORE, 4.0)
            put(COL_DATE, "2022-05-08")
            put(COL_GRADE, "VIP")
            put(COL_PRICE, 14)
            put(COL_ACTOR, "카이")
            put(COL_THEATER, "샤롯데시어터")
            put(COL_PRODUCTION, "오디컴퍼니")
            put(COL_REVIEW, "한 사람 안의 선과 악, 두 얼굴의 파멸적 선택.")
        }
        db?.insert(TABLE_NAME, null, values)
        //데스노트
        values = ContentValues().apply{
            put(COL_IMAGE, R.mipmap.deathnote)
            put(COL_MUSICAL, "데스노트")
            put(COL_SCORE, 4.5)
            put(COL_DATE, "2022-05-24")
            put(COL_GRADE, "VIP")
            put(COL_PRICE, 16)
            put(COL_ACTOR, "고은성, 김성철")
            put(COL_THEATER, "충무아트센터")
            put(COL_PRODUCTION, "오디컴퍼니")
            put(COL_REVIEW, "죽음을 손에 쥔 천재들, 정의와 광기의 치열한 두뇌 싸움.")
        }
        db?.insert(TABLE_NAME, null, values)

        //킹키부츠
        values = ContentValues().apply{
            put(COL_IMAGE, R.mipmap.kinkyboots)
            put(COL_MUSICAL, "킹키부츠")
            put(COL_SCORE, 5.0)
            put(COL_DATE, "2022-08-23")
            put(COL_GRADE, "VIP")
            put(COL_PRICE, 15)
            put(COL_ACTOR, "신재범, 강홍석")
            put(COL_THEATER, "충무아트센터")
            put(COL_PRODUCTION, "CJ ENM MUSICAL")
            put(COL_REVIEW, "누군가의 구두를 신는다는 건 그 사람을 이해하는 첫걸음이다.")
        }
        db?.insert(TABLE_NAME, null, values)

        //레베카
        values = ContentValues().apply{
            put(COL_IMAGE, R.mipmap.rebecca)
            put(COL_MUSICAL, "레베카")
            put(COL_SCORE, 3.5)
            put(COL_DATE, "2023-10-23")
            put(COL_GRADE, "VIP")
            put(COL_PRICE, 17)
            put(COL_ACTOR, "웬디, 옥주현")
            put(COL_THEATER, "블루스퀘어 신한카드홀")
            put(COL_PRODUCTION, "EMK 뮤지컬컴퍼니")
            put(COL_REVIEW, "잊히지 않는 그녀의 그림자, 레베카가 돌아왔다.")
        }
        db?.insert(TABLE_NAME, null, values)

        //벤허
        values = ContentValues().apply{
            put(COL_IMAGE, R.mipmap.benhur)
            put(COL_MUSICAL, "벤허")
            put(COL_SCORE, 4.0)
            put(COL_DATE, "2023-11-18")
            put(COL_GRADE, "VIP")
            put(COL_PRICE, 17)
            put(COL_ACTOR, "서경수, 규현")
            put(COL_THEATER, "LG아트센터")
            put(COL_PRODUCTION, "EMK 뮤지컬컴퍼니")
            put(COL_REVIEW, "배신과 복수, 그리고 용서까지… 불멸의 대서사.")
        }
        db?.insert(TABLE_NAME, null, values)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}