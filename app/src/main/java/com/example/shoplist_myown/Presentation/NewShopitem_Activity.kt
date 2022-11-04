package com.example.shoplist_myown.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist_myown.Domain.Shopitem
import com.example.shoplist_myown.R
import com.google.android.material.textfield.TextInputLayout

class NewShopitem_Activity : AppCompatActivity() {
    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var buttonAdd: Button
    private lateinit var viewModel: NewShopitemViewModel
    private var screenmode = UNKNOWN_SCREEN_MODE
    private var shopitemID = Shopitem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_shopitem)
        parseIntent()
        viewModel = ViewModelProvider(this)[NewShopitemViewModel::class.java]
        initViews()
        addTextChangeTextListener()
        luanchRightMode()
    }

    private fun luanchRightMode() {
        when(screenmode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
        viewModel.errorCount.observe(this){
            val message = if (it){
                getString(R.string.error_input_cout)
            } else null
            til_count.error = message
        }

        viewModel.errorName.observe(this){
            val message = if (it){
                getString(R.string.error_input_name)
            } else null
            til_name.error = message
        }

        viewModel.closeWindow.observe(this){
            finish()
        }
    }

    fun launchEditMode() {
        viewModel.getShopitemByID(shopitemID)
        viewModel.shopitem.observe(this){
            et_name.setText(it.name)
            et_count.setText(it.count.toString())
        }
        buttonAdd.setOnClickListener {
            viewModel.editShopitem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    fun launchAddMode() {
        buttonAdd.setOnClickListener {
            viewModel.addShopitem(et_name.text?.toString(), et_count.text?.toString())
        }
    }



    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Intent has no $EXTRA_MODE")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Intent has no mode $mode")
        }
        screenmode = mode
        if (screenmode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)){
                throw RuntimeException("Intent has no itemID")
                }
            shopitemID = intent.getIntExtra(EXTRA_ITEM_ID, Shopitem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        til_name = findViewById(R.id.til_name)
        til_count = findViewById(R.id.til_count)
        et_name = findViewById(R.id.et_name)
        et_count = findViewById(R.id.et_count)
        buttonAdd = findViewById(R.id.buttonAdd)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val UNKNOWN_SCREEN_MODE = ""

        fun intentAdd(context: Context): Intent {
            val intent = Intent(context, NewShopitem_Activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun intentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, NewShopitem_Activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, id)
            return intent
        }
    }

    private fun addTextChangeTextListener() {
        et_name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        et_count.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}