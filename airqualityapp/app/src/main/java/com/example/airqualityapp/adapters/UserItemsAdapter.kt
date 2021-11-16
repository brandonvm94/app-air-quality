package com.example.airqualityapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityapp.R
import com.example.airqualityapp.activities.UsersListActivity
import com.example.airqualityapp.model.User
import kotlinx.android.synthetic.main.user_item_list.view.*

open class UserItemsAdapter(
    private val context: Context,
    private var list: ArrayList<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_item_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.user_item_name.text = model.name
            holder.itemView.user_item_email.text = model.email

            holder.itemView.user_list_edit_btn.setOnClickListener {
                if (context is UsersListActivity) {
                    context.goToEditUser(model)
                }
            }

            holder.itemView.user_list_delete_btn.setOnClickListener {
                alertDialogForDeleteUser(position, model)
            }
        }
    }

    private fun alertDialogForDeleteUser(position: Int, user: User) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Eliminar Usuario")
        builder.setMessage("Estas seguro que quieres eliminar el usuario ${user.name}?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        // Yes - Answer
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.dismiss()

            if (context is UsersListActivity) {
                context.deleteUser(user.id)
            }
        }

        // NO - Answer
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}