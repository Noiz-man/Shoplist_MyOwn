package com.example.shoplist_myown.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shoplist_myown.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ShoplistViewModel
    private lateinit var rv_shoplist: RecyclerView
    private lateinit var shoplistadapter: ShoplistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[ShoplistViewModel::class.java]
        viewModel.shoplist.observe(this) {
            shoplistadapter.submitList(it)
        }

    }

    fun setupRecyclerView() {
        rv_shoplist = findViewById(R.id.rv_shoplist)
        shoplistadapter = ShoplistAdapter()
        rv_shoplist.adapter = shoplistadapter
        onClickListener()
        onLongClickListener()
    }

    fun onClickListener() {
        shoplistadapter.onShopitemClickListener = {
            viewModel.editShopitemState(it)
        }
    }

    fun onLongClickListener() {
        shoplistadapter.onShopitemLongClickListener = {
            viewModel.deleteShopitem(it)
        }
    }
}