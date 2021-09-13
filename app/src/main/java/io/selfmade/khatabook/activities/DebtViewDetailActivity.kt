package io.selfmade.khatabook.activities

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.selfmade.khatabook.R
import io.selfmade.khatabook.adapter.DebtDetailAdapter
import io.selfmade.khatabook.databinding.ActivityDebtViewDetailBinding
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.model.DebtUser
import io.selfmade.khatabook.utilities.Status
import io.selfmade.khatabook.viewmodel.DebtViewModel

class DebtViewDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDebtViewDetailBinding
    private lateinit var model: DebtViewModel
    private lateinit var adapter: DebtDetailAdapter
    private var list = ArrayList<DebtUser>()
    private lateinit var createUser: CreateUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDebtViewDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this@DebtViewDetailActivity)
            .get(DebtViewModel::class.java)

        createUser = intent.getSerializableExtra("data") as CreateUser

        initToolbar()
        initRecyclerView()

        model.getAllDebts(activity, createUser.id.toString())
            .observe(this@DebtViewDetailActivity, {
                if (it.status == Status.SUCCESS) {

                    if (adapter != null) {
                        adapter.addAll(it.data!!)
                    }
                }
            })

        binding.txtSubTotal.text = "Sub Total "+model.getTotal(activity, createUser.id.toString())

    }

    private fun initToolbar() {

        binding.mToolbar.mToolbar.title = "Detail"
        binding.mToolbar.mToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.mToolbar.mToolbar)
        binding.mToolbar.mToolbar.setNavigationIcon(R.drawable.ic_close)
        binding.mToolbar.mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun initRecyclerView() {
        adapter = DebtDetailAdapter(activity, list)
        binding.mRecyclerView.setHasFixedSize(false)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = adapter

    }

}