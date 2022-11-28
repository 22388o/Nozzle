package com.kaiwolfram.nozzle.ui.app

import com.kaiwolfram.nozzle.ui.app.views.chat.ChatViewModel
import com.kaiwolfram.nozzle.ui.app.views.feed.FeedViewModel
import com.kaiwolfram.nozzle.ui.app.views.keys.KeysViewModel
import com.kaiwolfram.nozzle.ui.app.views.profile.ProfileViewModel
import com.kaiwolfram.nozzle.ui.app.views.relays.RelaysViewModel
import com.kaiwolfram.nozzle.ui.app.views.support.SupportViewModel

data class VMContainer(
    val profileViewModel: ProfileViewModel,
    val feedViewModel: FeedViewModel,
    val chatViewModel: ChatViewModel,
    val keysViewModel: KeysViewModel,
    val relaysViewModel: RelaysViewModel,
    val supportViewModel: SupportViewModel,
) {
}
