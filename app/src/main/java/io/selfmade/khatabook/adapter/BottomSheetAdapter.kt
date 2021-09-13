package io.selfmade.khatabook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.selfmade.khatabook.databinding.ItemRowTwoBinding
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.CreateUser

class BottomSheetAdapter(
    var context: Context,
    var list: ArrayList<CreateItem>,
    var item: doItemSelect
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BottomSheetHolder(ItemRowTwoBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as BottomSheetHolder
        h.apply {
            it.txtItemName.text = list[position].itemName

            it.mCardView.setOnClickListener {
                item.doItem(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BottomSheetHolder(var it: ItemRowTwoBinding) : RecyclerView.ViewHolder(it.txtItemName)

    interface doItemSelect {
        fun doItem(position: Int)
    }
}