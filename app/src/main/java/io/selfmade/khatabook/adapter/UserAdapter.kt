package io.selfmade.khatabook.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import io.selfmade.khatabook.R
import io.selfmade.khatabook.activities.DebtViewDetailActivity
import io.selfmade.khatabook.activities.MainActivity
import io.selfmade.khatabook.databinding.UserListRowBinding
import io.selfmade.khatabook.model.CreateUser
import java.io.File

class UserAdapter(private val activity: Activity, private var list: ArrayList<CreateUser>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(UserListRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {

        val holder = h as Holder
        holder.apply {

            view.txtUserName.text = list[position].userName
            view.txtPhoneNumber.text = list[position].phoneNumber
            view.imgUser.setImageURI(Uri.fromFile(File(list[position].userProfile)))

            view.mCardView.setOnClickListener {

                val mainActivity = activity as MainActivity

                val bundle = Bundle()
                bundle.putSerializable("data", list[position])
                bundle.putSerializable("label", list[position].userName)
                mainActivity.getNavController()
                    .navigate(R.id.action_HomeFragment_to_AddDebitFragment, bundle)
//                mainActivity.getNavController().navigate(R.id.addDebitFragment, bundle)

            }

            view.imgUser.setOnClickListener {

                val intent = Intent(activity, DebtViewDetailActivity::class.java)
                intent.putExtra("data", list[position])
                activity.startActivity(intent)

            }
        }
    }

    fun addAll(list: List<CreateUser>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(var view: UserListRowBinding) : RecyclerView.ViewHolder(view.root)


}