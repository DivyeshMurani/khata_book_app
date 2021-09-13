package io.selfmade.khatabook.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.selfmade.khatabook.databinding.ItemRowBinding
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.utilities.Constance

class ItemAdapter(
    var activity: Activity,
    var list: ArrayList<CreateItem>,
    var delete: OnDeleteItem
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(ItemRowBinding.inflate(LayoutInflater.from(activity)))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as ItemHolder
        h.apply {
            it.txtItemName.text = list[position].itemName

            it.imgDelete.setOnClickListener {

                Constance.showDialog(
                    activity, "Delete", "You want to delete ${list[position].itemName}"
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

    fun addAll(it: List<CreateItem>) {

        list.addAll(it)
        notifyDataSetChanged()
    }

    fun addItem(it: CreateItem) {
        list.add(0, it)
        notifyItemRangeChanged(0, list.size)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    class ItemHolder(var it: ItemRowBinding) : RecyclerView.ViewHolder(it.root) {

    }

    public interface OnDeleteItem {
        fun onDelete(position: Int)
    }
}