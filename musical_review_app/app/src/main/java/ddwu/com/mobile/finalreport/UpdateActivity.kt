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
import ddwu.com.mobile.finalreport.databinding.ActivityUpdateBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import java.util.Locale
import kotlin.compareTo

class UpdateActivity : AppCompatActivity() {
    lateinit var updateBinding: ActivityUpdateBinding
    lateinit var musicalDao: MusicalDao
    override fun onCreate(savedInstanceState: Bundle?) {
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(updateBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        musicalDao = MusicalDao(this)
        val updateDto = intent.getSerializableExtra("update_musical") as MusicalDto
        Log.d("UPDATE", "전달받은 DTO: $updateDto")

        updateBinding.tvId.text = "뮤지컬 수정(ID: ${updateDto.id.toString()})"
        updateBinding.tvImage.setImageResource(updateDto.image)
        updateBinding.updateName.setHint(updateDto.musical)
        updateBinding.updateRating.rating = updateDto.score.toFloat()
        updateBinding.updateDate.date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(updateDto.date).time ?: System.currentTimeMillis()
        updateBinding.updateSpinner.setSelection(resources.getStringArray(R.array.grade_array).indexOf(updateDto.grade))
        updateBinding.updatePrice.setHint(updateDto.price.toString() + "만원")
        updateBinding.updateActor.setHint(updateDto.actor)
        updateBinding.updateTheater.setHint(updateDto.theater)
        updateBinding.updateProduction.setHint(updateDto.production)
        updateBinding.updateReview.setHint(updateDto.review)

        updateBinding.updateDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            updateBinding.updateDate.date = cal.timeInMillis
        }

        updateBinding.btnUpdate.setOnClickListener {
            val musical = updateBinding.updateName.text.toString()
            val score = updateBinding.updateRating.rating.toDouble()
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(updateBinding.updateDate.date ))
            var grade = updateBinding.updateSpinner.selectedItem.toString().ifEmpty{updateDto.grade}
            val price = updateBinding.updatePrice.text.toString().toIntOrNull() ?: updateDto.price
            var actor = updateBinding.updateActor.text.toString().ifEmpty {updateDto.actor}
            var theater = updateBinding.updateTheater.text.toString().ifEmpty {updateDto.theater}
            var production = updateBinding.updateProduction.text.toString().ifEmpty {updateDto.production}
            var review = updateBinding.updateReview.text.toString().ifEmpty{updateDto.review}
            if (musical.isEmpty()) {
                Toast.makeText(this, "필수 항목을 모두 입력해주세요(제목)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val result = musicalDao.updateMusical(MusicalDto(updateDto.id, updateDto.image, musical, score, date, grade, price, actor, theater, production, review))

            if(result > 0){
                setResult(RESULT_OK)
                finish()
            }
            else{
                Toast.makeText(this, "뮤지컬 수정 오류", Toast.LENGTH_SHORT).show()
            }
        }

        updateBinding.btnCancel2.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}