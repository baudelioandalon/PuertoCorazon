package com.boreal.puertocorazon.core.utils.bottomfragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.boreal.commonutils.base.CUBackHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class ABaseBottomSheetDialogFragment<T : ViewDataBinding>(
    private val extended: Boolean = true
) : BottomSheetDialogFragment() {

    private lateinit var cuBackHandler: CUBackHandler
    lateinit var binding: T
    private lateinit var dialog: BottomSheetDialog

    /**
     * Create BottomSheetDialogFragment with default methods
     * works with MVVM
     *
     * @param extended init state extended or collapsed
     */
    abstract fun getLayout(): Int
    abstract fun initView()
    open fun initDependency() {}
    open fun initObservers() {}
    open fun onDismissDialog() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        initView()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireContext(), theme)
        if (extended) {
            dialog.setOnShowListener {
                setExpanded()
            }
        }

        initDependency()
        initObservers()

        if (activity is CUBackHandler) {
            cuBackHandler = activity as CUBackHandler
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (extended) {
            dialog.setOnShowListener {
                setExpanded()
            }
        }
    }

    fun setExpanded() {
        val parentLayout =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        parentLayout?.let {
            val behaviour = BottomSheetBehavior.from(it)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.skipCollapsed = true
            behaviour.peekHeight = 0
        }
    }

    /**
     * @author DanielGC
     * @see Muestra el progressabar
     * @param message: String?
     * @param isCancelable: Boolean
     */
    fun showProgressBarCustom(message: String? = null, isCancelable: Boolean = false) {
        if (this::cuBackHandler.isInitialized) {
            cuBackHandler.showProgress(message, isCancelable)
        }
    }

    /**
     * @author DanielGC
     * @see Oculta el progressbar
     */
    fun hideProgressBarCustom() {
        if (this::cuBackHandler.isInitialized) {
            cuBackHandler.hideProgress()
        }
    }

    /**
     * @author DanielGC
     */
    fun hideKeyboard() {
        if (this::cuBackHandler.isInitialized) {
            cuBackHandler.hideKeyBoard()
        }
    }

    fun closeFragment() {
        dismissAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissDialog()
    }

}