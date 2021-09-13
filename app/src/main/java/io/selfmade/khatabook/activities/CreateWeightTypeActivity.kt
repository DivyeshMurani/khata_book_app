package io.selfmade.khatabook.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.selfmade.khatabook.R
import io.selfmade.khatabook.adapter.WeightTypeAdapter
import io.selfmade.khatabook.databinding.ActivityCreateWeightTypeBinding
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.WeightType
import io.selfmade.khatabook.utilities.Logs
import io.selfmade.khatabook.utilities.Status
import io.selfmade.khatabook.viewmodel.ModelFactory
import io.selfmade.khatabook.viewmodel.WeighTypeModel

class CreateWeightTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateWeightTypeBinding
    private lateinit var adapter: WeightTypeAdapter
    private var list = ArrayList<WeightType>()
    private lateinit var model: WeighTypeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateWeightTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(
            this@CreateWeightTypeActivity,
            ModelFactory()
        )[WeighTypeModel::class.java]


        initToolbar()
    }

    private fun initToolbar() {

        val mToolbar = binding.toolbar.mToolbar
        mToolbar.setTitleTextColor(Color.WHITE)
        mToolbar.title = "Create Weight Type"

        setSupportActionBar(mToolbar)

        mToolbar.setNavigationIcon(R.drawable.ic_close)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {

        adapter = WeightTypeAdapter(activity, list, object : WeightTypeAdapter.OnDeleteItem {
            override fun onDelete(position: Int) {
                adapter.removeAt(position)
            }
        })

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = adapter

        model.getAllWeighType(context = activity).observe(this@CreateWeightTypeActivity, {
            adapter.addAll(it!!)
        })
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

        val itemText = binding.tilWeightType.editText!!.text.toString()
        binding.tilWeightType.error = ""
        if (itemText.isEmpty()) {
            binding.tilWeightType.error = "Please enter type"
        } else {
            val weightType = WeightType(itemText)

            binding.tilWeightType.editText!!.text.clear()

            model.create(activity, weightType)
            val it = model.getLastWeighType(activity)

            adapter.addItem(it)

        }
    }


}