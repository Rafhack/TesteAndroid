package com.rafhack.testeandroid.form

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.rafhack.testeandroid.R
import com.rafhack.testeandroid.base.BaseProgressFragment
import com.rafhack.testeandroid.data.entities.form.Cell
import com.rafhack.testeandroid.data.entities.form.Type
import com.rafhack.testeandroid.di.component.DaggerFragmentComponent
import com.rafhack.testeandroid.di.module.FragmentModule
import javax.inject.Inject

class FormFragment : BaseProgressFragment(), FormContract.View {

    private lateinit var linContainer: LinearLayout
    private lateinit var ctlSuccess: ConstraintLayout
    private lateinit var tvwSendNew: TextView
    private var manager: DynamicFormManager? = null

    @Inject
    lateinit var presenter: FormPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.getCells()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.fragment_form, container, true)
        linContainer = view.findViewById(R.id.fragment_form_lin_content)
        ctlSuccess = view.findViewById(R.id.fragment_form_ctl_success)
        tvwSendNew = view.findViewById(R.id.fragment_form_tvw_send_new)
        setupListner()
        return view
    }

    private fun setupListner() {
        tvwSendNew.setOnClickListener {
            manager?.clearForm()
            linContainer.visibility = View.VISIBLE
            ctlSuccess.visibility = View.GONE
        }
    }

    private val validatedFieldsListener: ((type: Type) -> Unit) = {
        if (it == Type.SEND) {
            linContainer.visibility = View.GONE
            ctlSuccess.visibility = View.VISIBLE
        }
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(view?.findViewById(R.id.fragment_base_progress_ctl_root)!!,
                message, Snackbar.LENGTH_LONG).show()
    }

    override fun inflateCells(cells: ArrayList<Cell>) {
        manager = DynamicFormManager(linContainer, validatedFieldsListener)
        manager?.cells = cells
    }

}