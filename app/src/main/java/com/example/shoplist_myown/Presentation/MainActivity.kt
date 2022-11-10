package com.example.shoplist_myown.Presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist_myown.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShoplistFragment.OnFinishedFragmentListener {
    private lateinit var viewModel: ShoplistViewModel
    private lateinit var rv_shoplist: RecyclerView
    private lateinit var shoplistadapter: ShoplistAdapter
    private lateinit var fab: FloatingActionButton
    private var fragmentContainerView: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[ShoplistViewModel::class.java]
        viewModel.shoplist.observe(this) {
            shoplistadapter.submitList(it)
        }

        fragmentContainerView = findViewById(R.id.fragmentContainerView)

        fab = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            if (isScreenPortrait()) {
                val intent = NewShopitem_Activity.intentAdd(this)
                startActivity(intent)
            } else {
                launchFragment(ShoplistFragment.newInstanseAddTem())
            }
        }
    }

    private fun isScreenPortrait(): Boolean{
        return fragmentContainerView == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
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
            if (isScreenPortrait()) {
                val intent = NewShopitem_Activity.intentEdit(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShoplistFragment.newInstanseEditTem(it.id))
            }
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

    override fun finishedFragment() {
        Toast.makeText(this, "Все ОК", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}