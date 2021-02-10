package com.example.creditinfo.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creditinfo.R
import java.lang.StringBuilder

class SingleCustomerRecyclerviewAdapter(
    private val transactionDataSet: ArrayList<MainScreenActivity.CustomerTranscation>,
    singleCustomerActivity: SingleCustomerActivity
) :
    RecyclerView.Adapter<SingleCustomerRecyclerviewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val transactionDate: TextView
        val trasanctionBalance : TextView

        init {
            // Define click listener for the ViewHolder's View.
            transactionDate = view.findViewById(R.id.recyclerview_customer_name)
            trasanctionBalance = view.findViewById(R.id.customer_balance)
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
        val stringBuilder = StringBuilder()

        viewHolder.transactionDate.text = stringBuilder.append(transactionDataSet[position].transactionType).append("\n")
            .append(transactionDataSet[position].transactionDate)
        viewHolder.trasanctionBalance.text = transactionDataSet[position].amount
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = transactionDataSet.size

}