package ddwu.com.mobile.finalreport.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.databinding.MusicalListBinding

class MusicalAdapter(var musicals: List<MusicalDto>) : RecyclerView.Adapter<MusicalAdapter.MusicalViewHolder>(){
    override fun getItemCount(): Int = musicals.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicalViewHolder {
        val binding = MusicalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicalViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MusicalViewHolder,
        position: Int
    ) {
        Log.d("Adapter", "Image ID = ${musicals[position].image}")
        holder.binding.imageView.setImageResource(musicals[position].image)
        holder.binding.tvName.text = musicals[position].musical
        holder.binding.tvDetail.text = musicals[position].review
        holder.binding.tvScore.text = musicals[position].score.toString()
        holder.binding.tvDate.text = musicals[position].date
    }

    inner class MusicalViewHolder(val binding: MusicalListBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                click?.onItemClick(it, adapterPosition)
            }
            binding.root.setOnLongClickListener {
                longClick?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    var click : OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?){
        this.click = listener
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int): Boolean
    }

    var longClick: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?){
        this.longClick = listener
    }
}