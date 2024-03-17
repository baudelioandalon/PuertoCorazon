package com.boreal.puertocorazon.addevent.ui.gallery

import android.net.Uri
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.*
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.addevent.R
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.adm.addevent.databinding.PcGalleryAddEventFragmentBinding
import com.boreal.puertocorazon.core.component.bottomsheet.ABottomSheetOptionsImageFragment
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageToUploadItemModel
import com.boreal.puertocorazon.uisystem.databinding.PcGalleryToUploadItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCGalleryAddEventFragment : CUBaseFragment<PcGalleryAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    val adapterRecyclerImagesGallery by lazy {
        GAdapter<PcGalleryToUploadItemBinding, PCImageToUploadItemModel>(
            uiR.layout.pc_gallery_to_upload_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCImageToUploadItemModel>() {
                override fun areItemsTheSame(
                    oldItem: PCImageToUploadItemModel,
                    newItem: PCImageToUploadItemModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCImageToUploadItemModel,
                    newItem: PCImageToUploadItemModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {
                    if (list.indexOfFirst { it.imageToUpdate != Uri.EMPTY } != -1) {
                        binding.btnAddGallery.showView()
                    } else {
                        binding.btnAddGallery.invisibleView()
                    }
                    if (model.imageToUpdate == Uri.EMPTY) {
                        containerGalleryEmpty.showView()
                        containerGalleryFilled.hideView()
                        containerGalleryEmpty.onClick {
                            ABottomSheetOptionsImageFragment {
                                model.imageToUpdate = it
                                adapter.notifyItemChanged(position)
                                binding.tvErrorMessage.text = ""
                            }.show(
                                requireActivity().supportFragmentManager,
                                "imageoption"
                            )
                        }
                    } else {
                        containerGalleryEmpty.hideView()
                        containerGalleryFilled.showView()
                        imageFromGallery.setImageURI(model.imageToUpdate)
                        imageFromGallery.onClick {
                            showImageViewer(list.map { it.imageToUpdate }
                                .sortedBy { it != model.imageToUpdate })
                        }
                        btnRemoveImage.onClick {
                            if (list.size > 1) {
                                adapter.removeAt(position)
                            } else if (list.size == 1) {
                                model.imageToUpdate = Uri.EMPTY
                                adapter.notifyItemChanged(position)
                            }
                        }
                    }
                }
            }, onListChanged = { _, currentList ->
                binding.apply {
                    if (currentList.size == 1) {
                        mRecyclerImages.itemPercent(1.0)
                    } else if (currentList.size == 2) {
                        mRecyclerImages.itemPercent(.88)
                    }
                    mRecyclerImages.smoothScrollToPosition(currentList.size - 1)
                }

            }
        )
    }

    override fun getLayout() = R.layout.pc_gallery_add_event_fragment

    override fun initView() {
        initElements()
    }
}