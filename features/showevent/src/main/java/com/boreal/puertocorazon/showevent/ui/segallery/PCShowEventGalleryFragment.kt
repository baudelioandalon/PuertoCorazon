package com.boreal.puertocorazon.showevent.ui.segallery

import android.os.Bundle
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showImageViewer
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageItemModel
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.showevent.databinding.PcShowEventGalleryFragmentBinding
import com.boreal.puertocorazon.uisystem.databinding.PcGalleryItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCShowEventGalleryFragment :
    CUBaseFragment<PcShowEventGalleryFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerImagesGallery by lazy {
        GAdapter<PcGalleryItemBinding, PCImageItemModel>(
            R.layout.pc_gallery_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCImageItemModel>() {
                override fun areItemsTheSame(
                    oldItem: PCImageItemModel,
                    newItem: PCImageItemModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCImageItemModel,
                    newItem: PCImageItemModel
                ) = oldItem.imageUrl == newItem.imageUrl

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {
                    imageUrl = model.imageUrl
                    containerGalleryFilled.setOnSingleClickListener {
                        showImageViewer(list.map { it.imageUrl ?: "" }.sortedBy { it != model.imageUrl ?: "" })
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_show_event_gallery_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}