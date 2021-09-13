package io.selfmade.khatabook.adapter

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.selfmade.khatabook.databinding.RowWeightTypeBinding
import io.selfmade.khatabook.model.WeightType
import io.selfmade.khatabook.utilities.Constance
import io.selfmade.khatabook.utilities.Logs

class WeightTypeAdapter(
    var activity: Activity,
    var list: ArrayList<WeightType>,
    var delete: OnDeleteItem
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeightTypeHolder(RowWeightTypeBinding.inflate(activity.layoutInflater))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as WeightTypeHolder
        h.apply {

            it.txtItemName.text = list[position].weightTypeName

            it.imgDelete.setOnClickListener {

                Constance.showDialog(
                    activity, "Delete", "You want to delete ${list[position].weightTypeName}"
                ) { p0, p1 ->
                    p0.dismiss()
                    delete.onDelete(position)

                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(it: List<WeightType>): Unit {
        list.addAll(it)
        notifyDataSetChanged()
        Logs.e("list size ${list.size}")
    }

    fun addItem(it: WeightType) {
        list.add(0, it)
        notifyItemRangeChanged(0, list.size)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }


    class WeightTypeHolder(var it: RowWeightTypeBinding) : RecyclerView.ViewHolder(it.root)


    public interface OnDeleteItem {
        fun onDelete(position: Int)
    }


}