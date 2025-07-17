package ddwu.com.mobile.finalreport.data

import java.io.Serializable

data class MusicalDto(val id: Long?, val image: Int, val musical: String, val score: Double, val date: String, val grade: String, val price: Int, val actor: String, val theater: String, val production: String, val review: String) : Serializable {

}