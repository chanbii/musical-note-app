package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddwu.com.mobile.finalreport.data.MusicalDao
import ddwu.com.mobile.finalreport.data.MusicalDto
import ddwu.com.mobile.finalreport.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.compareTo

class AddActivity : AppCompatActivity() {
    lateinit var addBinding: ActivityAddBinding
    lateinit var musicalDao: MusicalDao
    override fun onCreate(savedInstanceState: Bundle?) {
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(addBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addBinding.imageView3.setImageResource(R.mipmap.musical_icon)
        musicalDao = MusicalDao(this)

        addBinding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            addBinding.calendarView.date = cal.timeInMillis
        }

        addBinding.btnAdd.setOnClickListener {
            val image: Int = R.mipmap.musical
            val musical = addBinding.etName.text.toString()
            val score = addBinding.ratingBar.rating
            val dateMillis = addBinding.calendarView.date
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.format(Date(dateMillis))
            var grade = addBinding.spinner.selectedItem.toString()
            val price = addBinding.etPrice.text.toString().toIntOrNull() ?: 0
            var actor = addBinding.etActor.text.toString().ifEmpty { "정보 없음" }
            var theater = addBinding.etTheater.text.toString().ifEmpty { "정보 없음" }
            var production = addBinding.etProduction.text.toString().ifEmpty { "정보 없음" }
            var review = addBinding.etReview.text.toString()
            if (musical.isEmpty() || review.isEmpty() || score == 0.0f) {
                Toast.makeText(this, "필수 항목을 모두 입력해주세요(제목, 평점, 리뷰)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("AddActivity", "Image ID: $image")
            val result = musicalDao.addMusical(MusicalDto(null, image, musical, score.toDouble(), date, grade, price, actor, theater, production, review))
            if(result > 0){
                setResult(RESULT_OK)
                finish()
            }
            else{
                Toast.makeText(this@AddActivity, "뮤지컬 추가 오류", Toast.LENGTH_SHORT).show()
            }
        }

        addBinding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}