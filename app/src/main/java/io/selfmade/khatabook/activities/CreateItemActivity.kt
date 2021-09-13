package io.selfmade.khatabook.activities

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.selfmade.khatabook.R
import io.selfmade.khatabook.adapter.ItemAdapter
import io.selfmade.khatabook.databinding.ActivityCreateItemBinding
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.utilities.Constance
import io.selfmade.khatabook.utilities.Logs

class CreateItemActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateItemBinding
    private lateinit var adapter: ItemAdapter
    private var list = ArrayList<CreateItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initToolbar()
    }

    private fun initToolbar() {

        val mToolbar = binding.toolbar.mToolbar
        mToolbar.setTitleTextColor(Color.WHITE)
        mToolbar.title = "Create Item"

        setSupportActionBar(mToolbar)

        mToolbar.setNavigationIcon(R.drawable.ic_close)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_checked) {
            submit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun submit() {

        val itemText = binding.tilItem.editText!!.text.toString()
        binding.tilItem.error = ""
        if (itemText.isEmpty()) {
            binding.tilItem.error = "Please enter item"
        } else {
            val createItem = CreateItem(itemText)
            dataViewModel.createItem(activity, createItem)
            binding.tilItem.editText!!.text.clear()

            val it = dataViewModel.item(activity)

            Logs.e("it.itemName :: ${it.itemName}")

            adapter.addItem(it)

        }
    }

    private fun initRecyclerView() {

        adapter = ItemAdapter(activity, list, object : ItemAdapter.OnDeleteItem {

            override fun onDelete(position: Int) {

                val isUsed = dataViewModel.itemUsed(activity, list[position].id.toString())
                if (isUsed) {
                    Constance.showDialog(
                        activity,
                        "Message",
                        "You can not delete ${list[position].itemName}. Because of this is using"
                    )
                    return
                }

                val it = list[position]
                dataViewModel.deleteItem(activity, it)
                adapter.removeAt(position)

            }
        })



        adapter.addAll(dataViewModel.allItem(activity))

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = adapter

    }
}