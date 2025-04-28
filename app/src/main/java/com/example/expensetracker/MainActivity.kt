package com.example.expensetracker // Make sure this matches your actual package name

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.expense_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data for RecyclerView
        val expenses = mutableListOf(
            Expense(100.0, "Food", "2025-04-24"),
            Expense(50.0, "Transport", "2025-04-23"),
            Expense(200.0, "Entertainment", "2025-04-22")
        )

        // Set Adapter for RecyclerView
        val adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        // Button to add a new expense
        val addButton: Button = findViewById(R.id.add_expense_button)
        addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add Expense")

            // Create an EditText to get the amount
            val amountInput = EditText(this)
            amountInput.hint = "Enter amount"
            builder.setView(amountInput)

            builder.setPositiveButton("Add") { dialog, _ ->
                val amount = amountInput.text.toString().toDoubleOrNull()
                if (amount != null) {
                    // Create a new expense
                    val expense = Expense(amount, "Other", "2025-04-24")
                    expenses.add(expense)

                    // Notify adapter of new data
                    adapter.notifyItemInserted(expenses.size - 1)
                }
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }

    // Data class for Expense
    data class Expense(val amount: Double, val category: String, val date: String)

    // Adapter for the RecyclerView
    class ExpenseAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
            return ExpenseViewHolder(view)
        }

        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            val expense = expenses[position]
            holder.amount.text = "Amount: ${expense.amount}"
            holder.category.text = "Category: ${expense.category}"
            holder.date.text = "Date: ${expense.date}"
        }

        override fun getItemCount(): Int {
            return expenses.size
        }

        // ViewHolder for Expense
        class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val amount: TextView = itemView.findViewById(R.id.expense_amount)
            val category: TextView = itemView.findViewById(R.id.expense_category)
            val date: TextView = itemView.findViewById(R.id.expense_date)
        }
    }
}
