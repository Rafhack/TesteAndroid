package com.rafhack.testeandroid.form.customCells

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.rafhack.testeandroid.R
import com.rafhack.testeandroid.data.entities.form.Cell

class CustomCellType5 : ConstraintLayout {
    var cell: Cell? = null
        set(value) = updateCell(value)

    lateinit var btnButton: Button

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupView()
    }

    private fun setupView() {
        View.inflate(context, R.layout.dynamic_form_cell_type_5, this)
        btnButton = findViewById(R.id.dynamic_form_cell_type_5_btn_button)
    }

    private fun updateCell(value: Cell?) {
        btnButton.text = value?.message
    }
}