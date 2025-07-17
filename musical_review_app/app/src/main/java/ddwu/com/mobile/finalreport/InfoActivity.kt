package ddwu.com.mobile.finalreport

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddwu.com.mobile.finalreport.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    lateinit var infoBinding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        infoBinding = ActivityInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(infoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        infoBinding.developer.setImageResource(R.mipmap.developer)
        infoBinding.btnCancel3.setOnClickListener {
            finish()
        }
    }
}