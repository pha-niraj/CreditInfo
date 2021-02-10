package com.example.creditinfo.ui.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.creditinfo.R

class MainRecyclerviewAdapter(
    private val dataSet: ArrayList<MainScreenActivity.CustomerInfo>,
    val mainScreenActivity: MainScreenActivity
) :
    RecyclerView.Adapter<MainRecyclerviewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView
        val customerBalance : TextView
        val recyclerItemView : ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View.
            customerName = view.findViewById(R.id.recyclerview_customer_name)
            customerBalance = view.findViewById(R.id.customer_balance)
            recyclerItemView = view.findViewById(R.id.recyclerview_item)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.customerName.text = dataSet[position].cutomerName
        viewHolder.customerBalance.text = dataSet[position].customerBalance
        viewHolder.recyclerItemView.setOnClickListener {
            val intent = Intent(mainScreenActivity,SingleCustomerActivity::class.java)
            intent.putExtra("position",position)
            mainScreenActivity.startActivity(intent)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
