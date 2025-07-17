package ddwu.com.mobile.finalreport
// 과제명: 뮤지컬 리뷰 관리 앱
// 분반: 02 분반
// 학번: 20211442 성명: 김찬비
// 제출일: 2025년 6월 26일
import android.app.ComponentCaller
import android.app.Dialog
import android.content.Intent
import android.icu.text.IDNA
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.MusicalAdapter
import ddwu.com.mobile.finalreport.data.MusicalDao
import ddwu.com.mobile.finalreport.data.MusicalDto
import ddwu.com.mobile.finalreport.databinding.ActivityMainBinding
import kotlin.compareTo

val ADDACTIVITY_CODE = 100
val UPDATEACTIVITY_CODE = 200
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var musicalDao: MusicalDao
    lateinit var adapter: MusicalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        musicalDao = MusicalDao(this)
        val allMusicals : List<MusicalDto> = musicalDao.getAllMusicals()
        adapter = MusicalAdapter(allMusicals)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MusicalAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.d(TAG, "클릭 위치: $position")
                val updateDto = adapter.musicals.get(position)
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("update_musical", updateDto)
                startActivityForResult(intent, UPDATEACTIVITY_CODE)
            }
        })

        adapter.setOnItemLongClickListener(object : MusicalAdapter.OnItemLongClickListener{
            override fun onItemLongClick(
                view: View,
                position: Int
            ): Boolean {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity).apply{
                    setTitle("데이터 삭제")
                    setMessage("${adapter.musicals[position].musical} 뮤지컬을 삭제하시겠습니까?")
                    setPositiveButton("삭제") {  _, _ ->
                        val id = adapter.musicals[position].id ?: 0
                        val result = musicalDao.remove(id)
                        if (result > 0) {
                            adapter.musicals = musicalDao.getAllMusicals()
                            adapter.notifyDataSetChanged()
                        }
                    }
                    setNegativeButton("취소", null)
                    setCancelable(false)
                }
                val dialog: Dialog = builder.create()
                dialog.show()
                return true
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "제목 검색"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                adapter.musicals = if(!newText.isNullOrBlank()){
                    musicalDao.searchByTitle(newText)
                } else {
                    musicalDao.getAllMusicals()
                }
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.musicals = if(!newText.isNullOrBlank()){
                    musicalDao.searchByTitle(newText)
                } else {
                    musicalDao.getAllMusicals()
                }
                adapter.notifyDataSetChanged()
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addMusical -> {
                val intent: Intent = Intent(this, AddActivity::class.java)
                startActivityForResult(intent, ADDACTIVITY_CODE)
                return true
            }
            R.id.info -> {
                val intent: Intent = Intent(this, InfoActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.exitApp -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this).apply{
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니까?")
                    setPositiveButton("종료") {
                        _, _ -> finish()
                    }
                    setNegativeButton("취소", null)
                    setCancelable(false)
                }
                val dialog: Dialog = builder.create()
                dialog.show()
                return true
            }
            R.id.sortByScore -> {
                adapter.musicals = musicalDao.sortByScore()
                adapter.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        if(requestCode == ADDACTIVITY_CODE){
            when(resultCode){
                RESULT_OK -> {
                    adapter.musicals = musicalDao.getAllMusicals()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "뮤지컬 추가 완료!", Toast.LENGTH_SHORT).show()
                }
                RESULT_CANCELED -> {

                }
            }
        }
        else if(requestCode == UPDATEACTIVITY_CODE){
            when(resultCode){
                RESULT_OK -> {
                    adapter.musicals = musicalDao.getAllMusicals()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "뮤지컬 수정 완료!", Toast.LENGTH_SHORT).show()
                }
                RESULT_CANCELED -> {

                }
            }
        }
    }
}