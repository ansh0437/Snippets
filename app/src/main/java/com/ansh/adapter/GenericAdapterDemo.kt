package com.ansh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ansh.R
import com.ansh.adapter.generic.GenericAdapter
import com.ansh.adapter.generic.GenericModel
import com.ansh.databinding.RowDemoBinding

data class User(val name: String)

class GenericAdapterDemo {

    fun init() {

        val users = listOf(User("One"), User("Two"), User("Three"))

        val list = users.map { UserModel(it, userListener) }

        val genericAdapter = GenericAdapter(list)

    }

    private val userListener: (User) -> Unit = {

    }

}

class UserModel(
    private val user: User,
    private val listener: (User) -> Unit
) : GenericModel<RowDemoBinding> {

    override fun getDataBinding(parent: ViewGroup): RowDemoBinding =
        RowDemoBinding.inflate(LayoutInflater.from(parent.context))

    override fun onBind(binding: RowDemoBinding, position: Int) {
        binding.tvName.text = user.name
        binding.tvName.setOnClickListener { listener(user) }
    }

}

/* BASE ADAPTER DEMO */

class Abc {

    init {
        val adapter = MyAdapter()
        adapter.updateData(listOf())
        adapter.addListener { user ->

        }
        adapter.addListener { user, i ->

        }
    }

}

class MyAdapter : BaseAdapter<User, RowDemoBinding>() {

    override fun getLayoutId() = R.layout.row_demo

    override fun onBind(binding: RowDemoBinding, data: User, position: Int) {
        dataListener?.invoke(data)
        dataPositionListener?.invoke(data, position)
    }

}