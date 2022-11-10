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
    private var screenmode = UNKNOWN_SCREEN_MODE
    private var shopitemID = Shopitem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_shopitem)
        if (savedInstanceState == null){
            launchRightMode()
        }
        parseIntent()

    }

    private fun launchRightMode() {
        val fragment = when(screenmode) {
            MODE_ADD -> ShoplistFragment.newInstanseAddTem()
            MODE_EDIT -> ShoplistFragment.newInstanseEditTem(shopitemID)
            else -> throw RuntimeException("Intent has no mode $screenmode")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.shopitem_container, fragment)
            .commit()
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
}