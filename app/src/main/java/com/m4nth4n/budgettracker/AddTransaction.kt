package com.m4nth4n.budgettracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.activity_add_transaction.amountInput
import kotlinx.android.synthetic.main.activity_add_transaction.amountLayout
import kotlinx.android.synthetic.main.activity_add_transaction.closeBtn
import kotlinx.android.synthetic.main.activity_add_transaction.descInput
import kotlinx.android.synthetic.main.activity_add_transaction.labelInput
import kotlinx.android.synthetic.main.activity_add_transaction.labelLayout
import kotlinx.android.synthetic.main.activity_detailed.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransaction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)


        labelInput.addTextChangedListener {
            if(it!!.count()>0)
                labelLayout.error = null
        }
        amountInput.addTextChangedListener {
            if(it!!.count()>0)
                amountLayout.error = null
        }

//        // This is for remove focus from the text fields after you update the text
//        rootView.setOnClickListener {
//            this.window.decorView.clearFocus()
//
//// this is to hide keyboard after touching outside the input field
//
//            val keyboardHide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            keyboardHide.hideSoftInputFromWindow(it.windowToken, 0)
//
//        }

        addTransactionBtn.setOnClickListener{
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descInput.text.toString()


            if (label.isEmpty())
                labelLayout.error = "Please enter a valid label!"
            else if (amount == null)
                amountLayout.error = "Please enter a valid amount!"
            else{
                val transaction= Transaction(0,label,amount,description)
                insert(transaction)
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }

    }
    private fun insert(transaction: Transaction){
        val db = Room.databaseBuilder(this,AppDatabase::class.java,"transactions").build()
        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }

}