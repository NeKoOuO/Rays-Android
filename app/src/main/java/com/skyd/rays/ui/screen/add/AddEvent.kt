package com.skyd.rays.ui.screen.add

import com.skyd.rays.base.IUiEvent
import com.skyd.rays.model.bean.StickerWithTags

class AddEvent(
    val getStickersWithTagsUiEvent: GetStickersWithTagsUiEvent? = null,
    val addStickersResultUiEvent: AddStickersResultUiEvent? = null,
    val recognizeTextUiEvent: RecognizeTextUiEvent? = null,
) : IUiEvent

sealed class GetStickersWithTagsUiEvent {
    class Success(val stickerWithTags: StickerWithTags) : GetStickersWithTagsUiEvent()
    object Init : GetStickersWithTagsUiEvent()
    object Failed : GetStickersWithTagsUiEvent()
}

sealed class AddStickersResultUiEvent {
    object Duplicate : AddStickersResultUiEvent()
    class Success(val stickerUuid: String) : AddStickersResultUiEvent()
}

sealed class RecognizeTextUiEvent {
    class Success(val texts: Set<String>) : RecognizeTextUiEvent()
}