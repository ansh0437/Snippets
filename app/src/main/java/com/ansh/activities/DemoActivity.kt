package com.ansh.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ansh.R
import com.ansh.core.adapter.generic.GenericAdapter
import com.ansh.core.adapter.generic.GenericModel
import com.ansh.core.databinding.RowListItemBinding
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    private val menuList = arrayListOf<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        menuList.add(Menu("Permission", PermissionDemo::class.java))

        val adapter =
            GenericAdapter<RowListItemBinding>()
        adapter.updateData(getMenuModels())
        recyclerView.adapter = adapter
    }

    private fun getMenuModels(): List<MenuModel> {
        return menuList.map { MenuModel(it, ::openDemo) }
    }

    private fun openDemo(aClass: Class<*>) {
        startActivity(Intent(this, aClass))
    }

}

data class Menu(
    val title: String,
    val cls: Class<*>
)

class MenuModel(
    private val menu: Menu,
    private val listener: (Class<*>) -> Unit
) : GenericModel<RowListItemBinding> {

    override fun getDataBinding(parent: ViewGroup): RowListItemBinding =
        RowListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(binding: RowListItemBinding, position: Int) {
        binding.tvName.text = menu.title
        binding.tvName.setOnClickListener { listener.invoke(menu.cls) }
    }

}