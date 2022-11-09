package com.example.shoplist_myown.Presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist_myown.Domain.Shopitem
import com.example.shoplist_myown.R
import com.google.android.material.textfield.TextInputLayout

class ShoplistFragment : Fragment() {

    private var screenmode: String = UNKNOWN_SCREEN_MODE
    private var shopitemID: Int = Shopitem.UNDEFINED_ID
    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var buttonAdd: Button
    private lateinit var viewModel: NewShopitemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_shopitem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NewShopitemViewModel::class.java]
        initViews(view)
        addTextChangeTextListener()
        luanchRightMode()

    }

    private fun luanchRightMode() {
        when (screenmode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
        viewModel.errorCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_cout)
            } else null
            til_count.error = message
        }

        viewModel.errorName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else null
            til_name.error = message
        }

        viewModel.closeWindow.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    fun launchEditMode() {
        viewModel.getShopitemByID(shopitemID)
        viewModel.shopitem.observe(viewLifecycleOwner) {
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


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_MODE)) {
            throw RuntimeException("Intent has no ${EXTRA_MODE}")
        }
        val mode = args.getString(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Intent has no mode $mode")
        }
        screenmode = mode
        if (screenmode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_ITEM_ID)) {
                throw RuntimeException("Intent has no itemID")
            }
            shopitemID = args.getInt(EXTRA_ITEM_ID, Shopitem.UNDEFINED_ID)
        }
    }


    private fun initViews(view: View) {
        til_name = view.findViewById(R.id.til_name)
        til_count = view.findViewById(R.id.til_count)
        et_name = view.findViewById(R.id.et_name)
        et_count = view.findViewById(R.id.et_count)
        buttonAdd = view.findViewById(R.id.buttonAdd)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val UNKNOWN_SCREEN_MODE = ""

        fun newInstanseAddTem(): ShoplistFragment {
            return ShoplistFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }
        }


        fun newInstanseEditTem(shopitemID: Int): ShoplistFragment {
            return ShoplistFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                    putInt(EXTRA_ITEM_ID, shopitemID)
                }
            }
        }
    }


    private fun addTextChangeTextListener() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        et_count.addTextChangedListener(object : TextWatcher {
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