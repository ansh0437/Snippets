package com.ansh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ansh.databinding.RowDemoBinding

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

//class MyAdapter(
//    list: List<User>,
//    private val listener: (User) -> Unit
//) : GenericAdapter<User, RowDemoBinding>(R.layout.row_demo, list) {
//
//    override fun onBind(binding: RowDemoBinding, data: User) {
//        binding.tvName.setOnClickListener { listener.invoke(data) }
//    }
//
//}

data class User(val name: String)