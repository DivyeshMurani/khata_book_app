package io.selfmade.khatabook.activities

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.selfmade.khatabook.R
import io.selfmade.khatabook.databinding.ActivityAddDebitBinding
import io.selfmade.khatabook.model.CreateItem
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.model.DebtUser
import io.selfmade.khatabook.model.WeightType
import io.selfmade.khatabook.utilities.DecimalDigitsInputFilter
import io.selfmade.khatabook.utilities.ItemSelectBottomDialog
import io.selfmade.khatabook.utilities.Logs
import io.selfmade.khatabook.utilities.Status
import io.selfmade.khatabook.viewmodel.DebtViewModel
import io.selfmade.khatabook.viewmodel.ModelFactory
import io.selfmade.khatabook.viewmodel.WeighTypeModel


class AddDebtActivity : BaseActivity(), ItemSelectBottomDialog.OnItemSelect {

    private lateinit var binding: ActivityAddDebitBinding

    private lateinit var createUserItem: CreateUser
    private var list = ArrayList<CreateItem>()
    private var listWeight = ArrayList<WeightType>()
    private var itemSelectedId = -1L
    private var itemSelect = ""
    private var weightSelectedId = -1L
    private var weightSelect = ""

    private lateinit var vieModel: DebtViewModel
    private lateinit var weighTypeModel: WeighTypeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddDebitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vieModel = ViewModelProvider(this, ModelFactory()).get(DebtViewModel::class.java)
        weighTypeModel = ViewModelProvider(this, ModelFactory()).get(WeighTypeModel::class.java)

        createUserItem = intent.getSerializableExtra("data") as CreateUser

        binding.tilPrice.editText!!.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(5, 2))

        initToolbar()
        initItemAdapter()
        totalInit()
    }

    private fun totalInit() {

        binding.tilPrice.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                subTotal(p0.toString(), binding.tilQty.editText!!.text.toString())
            }
        })

        binding.tilQty.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun afterTextChanged(p0: Editable?) {
                subTotal(binding.tilPrice.editText!!.text.toString(), p0!!.toString())

            }
        })
    }

    private fun subTotal(itemOne: String, itemTwo: String) {


        var itOne = itemOne
        var itTwo = itemTwo


        if (itemOne.isEmpty()) {
            itOne = "0"
        }
        if (itemTwo.isEmpty()) {
            itTwo = "0"
        }

        Logs.e("One :: ${itOne} AND ${itTwo}")

        try {
            binding.txtTotal.text = "Total :: ${(itOne.toBigDecimal() * itTwo.toBigDecimal())}"
        } catch (e: Exception) {
            Logs.e("Exception :: ${e.message}")
        }
    }

    private fun initWeightAdapter() {

        weighTypeModel.getAllWeighType(activity)
            .observe(this@AddDebtActivity, {

                listWeight.addAll(it)

                val itemArray = ArrayList<String>()
                for (item in it) {
                    itemArray.add(item.weightTypeName)
                }
                val adapter =
                    ArrayAdapter(
                        this@AddDebtActivity,
                        android.R.layout.simple_list_item_1,
                        itemArray
                    )

                binding.weightText.setAdapter(adapter)
            })

        binding.weightText.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                weightSelect = listWeight[p2].weightTypeName
                weightSelectedId = listWeight[p2].id!!
            }
        }
    }

    private fun initItemAdapter() {

        if (dataViewModel.allItem(activity).isEmpty()) {
            Toast.makeText(activity, "First insert Item", Toast.LENGTH_SHORT).show()
            onBackPressed()
            return
        } else if (weighTypeModel.allWeighTypeCheck(activity).size == 0) {
            Toast.makeText(activity, "First insert Weight", Toast.LENGTH_SHORT).show()
            onBackPressed()
            return
        }

        initWeightAdapter()

        dataViewModel.allItemX(activity).observe(this, Observer {
            Logs.e("it.status ::${it.status}")

            if (it.status == Status.SUCCESS) {
                list.addAll(it.data!!)
                val itemArray = ArrayList<String>()
                for (it in list) {
                    itemArray.add(it.itemName)
                }
                val adapter =
                    ArrayAdapter(
                        this@AddDebtActivity,
                        android.R.layout.simple_list_item_1,
                        itemArray
                    )
                binding.autoText.setAdapter(adapter)
            }

        })

        binding.autoText.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                itemSelect = list[p2].itemName
                itemSelectedId = list[p2].id!!
            }
        }
    }

    private fun initToolbar() {

        binding.toolbar.mToolbar.title = createUserItem.userName
        binding.toolbar.mToolbar.setTitleTextColor(Color.WHITE)
        binding.toolbar.mToolbar.setNavigationIcon(R.drawable.ic_close)
        setSupportActionBar(binding.toolbar.mToolbar)

        binding.toolbar.mToolbar.setNavigationOnClickListener {
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
        val price = binding.tilPrice.editText!!.text.toString()
        val qty = binding.tilQty.editText!!.text.toString()

        binding.tilPrice.error = ""
        binding.tilQty.error = ""
        binding.tiItem.error = ""
        binding.tilWeightType.error = ""

        when {
            itemSelectedId == -1L -> {
                binding.tiItem.error = "Please select item"
            }
            price.isEmpty() -> {
                binding.tilPrice.error = "Please enter price"
                return
            }

            weightSelectedId == -1L -> {
                binding.tilWeightType.error = "Please select weight"
            }

            qty.isEmpty() -> {
                binding.tilQty.error = "Please enter qty"
                return
            }

            else -> {
                val it = DebtUser(
                    createUserItem.id.toString(),
                    itemSelect,
                    itemSelectedId.toString(),
                    weightSelect,
                    weightSelectedId.toString(),
                    price,
                    qty
                )

                val has = vieModel.insertDebt(application, it)
                Logs.e("has :: ${has}")
                onBackPressed()

//                if (has == 1L) {
//                } else {
//                    Toast.makeText(activity, "Something wrong", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    //    not used today
    override fun onItemSelect(it: CreateItem) {
    }

}