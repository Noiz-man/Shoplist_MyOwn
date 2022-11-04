package com.example.shoplist_myown.Presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist_myown.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ShoplistViewModel
    private lateinit var rv_shoplist: RecyclerView
    private lateinit var shoplistadapter: ShoplistAdapter
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[ShoplistViewModel::class.java]
        viewModel.shoplist.observe(this) {
            shoplistadapter.submitList(it)
        }

        fab = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = NewShopitem_Activity.intentAdd(this)
            startActivity(intent)
        }
    }

    fun setupRecyclerView() {
        rv_shoplist = findViewById(R.id.rv_shoplist)
        shoplistadapter = ShoplistAdapter()
        rv_shoplist.adapter = shoplistadapter
        onClickListener()
        onLongClickListener()
        swipeToDelete()
    }

    fun onClickListener() {
        shoplistadapter.onShopitemClickListener = {
            viewModel.editShopitemState(it)
        }
    }

    fun onLongClickListener() {
        shoplistadapter.onShopitemLongClickListener = {
            val intent = NewShopitem_Activity.intentEdit(this, it.id)
            startActivity(intent)
        }
    }

    fun swipeToDelete() {
        val callback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shoplistadapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopitem(item)
            }
        }
        val itemtouchhelper = ItemTouchHelper(callback)
        itemtouchhelper.attachToRecyclerView(rv_shoplist)
    }
}