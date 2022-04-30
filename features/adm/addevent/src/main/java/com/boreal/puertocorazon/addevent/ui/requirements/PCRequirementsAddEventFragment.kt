package com.boreal.puertocorazon.addevent.ui.requirements

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.changeImgColor
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcRequirementsAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.core.domain.entity.requirements.PCAllowedRequirementToShowModel
import com.boreal.puertocorazon.uisystem.databinding.PcRequirementItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCRequirementsAddEventFragment : CUBaseFragment<PcRequirementsAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    val adapterAllowedPeople by lazy {
        GAdapter<PcRequirementItemBinding, PCAllowedRequirementToShowModel>(
            R.layout.pc_requirement_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCAllowedRequirementToShowModel>() {
                override fun areItemsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem.imageResource == newItem.imageResource

            }).build(),
            holderCallback = { bindingElement, model, _, adapter, position ->
                bindingElement.apply {
                    imgInside.changeDrawable(model.imageResource)
                    if (model.selected) {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.green_600)
                        imgInside.changeImgColor(R.color.white)
                    } else {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.red_500)
                        imgInside.changeImgColor(R.color.white)
                    }
                    allowedElement.setOnClickListener {
                        model.selected = !model.selected
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        )
    }

    val adapterAllowedClothes by lazy {
        GAdapter<PcRequirementItemBinding, PCAllowedRequirementToShowModel>(
            R.layout.pc_requirement_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCAllowedRequirementToShowModel>() {
                override fun areItemsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, _, adapter, position ->
                bindingElement.apply {
                    imgInside.changeDrawable(model.imageResource)
                    if (model.selected) {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.green_600)
                        imgInside.changeImgColor(R.color.white)
                    } else {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.red_500)
                        imgInside.changeImgColor(R.color.white)
                    }
                    allowedElement.setOnClickListener {
                        model.selected = !model.selected
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        )
    }

    val adapterAllowedAccesories by lazy {
        GAdapter<PcRequirementItemBinding, PCAllowedRequirementToShowModel>(
            R.layout.pc_requirement_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCAllowedRequirementToShowModel>() {
                override fun areItemsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCAllowedRequirementToShowModel,
                    newItem: PCAllowedRequirementToShowModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, _, adapter, position ->
                bindingElement.apply {
                    imgInside.changeDrawable(model.imageResource)
                    if (model.selected) {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.green_600)
                        imgInside.changeImgColor(R.color.white)
                    } else {
                        allowedElement.backgroundColor =
                            ContextCompat.getColor(requireContext(), R.color.red_500)
                        imgInside.changeImgColor(R.color.white)
                    }
                    allowedElement.setOnClickListener {
                        model.selected = !model.selected
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_requirements_add_event_fragment

    override fun initView() {
        initElements()
    }
}