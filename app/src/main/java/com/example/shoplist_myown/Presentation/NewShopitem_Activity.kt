package com.example.shoplist_myown.Presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist_myown.Domain.Shopitem
import com.example.shoplist_myown.R
import com.google.android.material.textfield.TextInputLayout

class NewShopitem_Activity : AppCompatActivity() {
    private var screenmode = EMPTY_STRING
    private var shopitemID = Shopitem.UNDEFINED_ID
    private lateinit var viewModel: NewShopitemViewModel
    private lateinit var btn_Save: Button
    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_shopitem)

        parseIntent()
        viewModel = ViewModelProvider(this)[NewShopitemViewModel::class.java]
        initViews()
        choiseMode()
        errors()
        resetErrors()


    }

    private fun errors() {
        viewModel.errorInputName.observe(this){
            val message = if(it) {
                "Ошибка ввода имени"
            } else null
            til_name.error = message
        }
        viewModel.errorInputCount.observe(this){
            val message = if (it) {
                "Ошибка ввода количества"
            } else null
            til_count.error = message
        }
        viewModel.closeWindow.observe(this){
            finish()
        }
    }

    private fun resetErrors() {
        et_name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        et_count.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }


    private fun choiseMode() {
        when(screenmode) {
            MODE_ADD -> addNewShopitem()
            MODE_EDIT -> editShopitem()
        }
    }

    private fun addNewShopitem() {
        btn_Save.setOnClickListener {
            viewModel.addShopitem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    private fun editShopitem() {
        viewModel.getItemByID(shopitemID)
        viewModel.shopitem.observe(this) {
            et_name.setText(it.name)
            et_count.setText(it.count.toString())
        }
        btn_Save.setOnClickListener {
            viewModel.editAhopitem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    private fun initViews() {
        til_name = findViewById(R.id.til_name)
        til_count = findViewById(R.id.til_count)
        et_name = findViewById(R.id.et_name)
        et_count = findViewById(R.id.et_count)
        btn_Save = findViewById(R.id.button)
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Данные не обнаружены")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Нет такого режима")
        }
        screenmode = mode
        if (screenmode == MODE_EDIT) {
            shopitemID = intent.getIntExtra(SHOP_ITEM_ID, Shopitem.UNDEFINED_ID)
        }

    }

    companion object {
        private const val EXTRA_MODE = "extra mode"
        private const val MODE_ADD = "mode add"
        private const val MODE_EDIT = "mode count"
        private const val SHOP_ITEM_ID = "shop item ID"
        private const val EMPTY_STRING = ""

        fun intentAdd(context: Context): Intent {
            val intent = Intent(context, NewShopitem_Activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun intentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, NewShopitem_Activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(SHOP_ITEM_ID, id)
            return intent
        }
    }
}