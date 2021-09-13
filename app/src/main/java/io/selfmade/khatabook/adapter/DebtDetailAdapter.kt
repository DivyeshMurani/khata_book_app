package io.selfmade.khatabook.adapter

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.selfmade.khatabook.databinding.RowDebtDetailBinding
import io.selfmade.khatabook.model.DebtUser

class DebtDetailAdapter(val activity: Activity, val list: ArrayList<DebtUser>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DebtHolder(RowDebtDetailBinding.inflate(activity.layoutInflater))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as DebtHolder
        val item = list[position]
        h.apply {
            it.txtItemName.text = item.item
            it.txtPrice.text = item.price
            it.txtQty.text = item.qty
            it.txtTotal.text = item.total
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(list: List<DebtUser>) {
        this.list.addAll(list)
        notifyDataSetChanged()

    }

    class DebtHolder(var it: RowDebtDetailBinding) : RecyclerView.ViewHolder(it.root)
}