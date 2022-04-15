package com.boreal.puertocorazon.core.utils

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boreal.commonutils.base.CUBackHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.reflect.KClass

abstract class ABaseBottomSheetDialogFragment<T : ViewDataBinding, V : ViewModel>(
    private val vkClass: KClass<V>,
    private val extended: Boolean = true
) : BottomSheetDialogFragment() {

    private lateinit var cuBackHandler: CUBackHandler

    lateinit var binding: T

    val viewModel by lazy {
        ViewModelProvider(this).get(vkClass.javaObjectType)
    }

    /**
     * @author Daniel Garcia & Baudelio Andalon
     * @since 21/09/2020
     * @see Obtener el layout
     */
    abstract fun getLayout(): Int
    abstract fun initDependency(savedInstanceState: Bundle?)
    open fun initObservers(){
        //Implementation here
    }
    abstract fun initView()
    open fun onDismissDialog(){

    }

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
        val dialog = BottomSheetDialog(requireContext(), theme)
        if (extended) {
            dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { parent ->
                    val behaviour = BottomSheetBehavior.from(parent)
                    val layoutParams: ViewGroup.LayoutParams
                    val param = parent.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(0, 130, 0, 0)
                    layoutParams = param

                    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                    parent.layoutParams = layoutParams
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    behaviour.skipCollapsed = true
                }
            }
        }

        initDependency(savedInstanceState)
        initObservers()

        if (activity is CUBackHandler) {
            cuBackHandler = activity as CUBackHandler
        }

        return dialog
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