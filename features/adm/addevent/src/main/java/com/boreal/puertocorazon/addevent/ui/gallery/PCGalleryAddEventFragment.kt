package com.boreal.puertocorazon.addevent.ui.gallery

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcGalleryAddEventFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageItemModel
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageToUploadItemModel
import com.boreal.puertocorazon.uisystem.databinding.PcGalleryItemBinding
import com.boreal.puertocorazon.uisystem.databinding.PcGalleryToUploadItemBinding

class PCGalleryAddEventFragment : CUBaseFragment<PcGalleryAddEventFragmentBinding>() {

    val adapterRecyclerImagesGallery by lazy {
        GAdapter<PcGalleryToUploadItemBinding, PCImageToUploadItemModel>(
            R.layout.pc_gallery_to_upload_item,
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
                    if (model.empty) {
                        containerGalleryEmpty.showView()
                        containerGalleryFilled.hideView()
                    } else {
                        containerGalleryEmpty.hideView()
                        containerGalleryFilled.showView()
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_gallery_add_event_fragment

    override fun initView() {
        initElements()
    }
}