package com.kaiwolfram.nozzle.data.profileFollower

import android.util.Log
import com.kaiwolfram.nozzle.data.nostr.INostrService
import com.kaiwolfram.nozzle.data.preferences.key.IPubkeyProvider
import com.kaiwolfram.nozzle.data.room.dao.ContactDao

private const val TAG = "ProfileFollower"

// TODO: Synchronize Contact list in nostr and db
class ProfileFollower(
    private val nostrService: INostrService,
    private val pubkeyProvider: IPubkeyProvider,
    private val contactDao: ContactDao,
) : IProfileFollower {

    override suspend fun follow(pubkeyToFollow: String) {
        Log.i(TAG, "Follow $pubkeyToFollow")
        contactDao.insert(
            pubkey = pubkeyProvider.getPubkey(),
            contactPubkey = pubkeyToFollow,
            createdAt = 0
        )

        val contacts = contactDao.listContactPubkeys(pubkey = pubkeyProvider.getPubkey())
        val event = nostrService.updateContactList(contacts = contacts)
        contactDao.updateTime(pubkey = pubkeyProvider.getPubkey(), createdAt = event.createdAt)
    }

    override suspend fun unfollow(pubkeyToUnfollow: String) {
        Log.i(TAG, "Unfollow $pubkeyToUnfollow")
        contactDao.deleteContact(
            pubkey = pubkeyProvider.getPubkey(),
            contactPubkey = pubkeyToUnfollow
        )
        val contacts = contactDao.listContactPubkeys(pubkey = pubkeyProvider.getPubkey())
        val event = nostrService.updateContactList(contacts = contacts)
        contactDao.updateTime(pubkey = pubkeyProvider.getPubkey(), createdAt = event.createdAt)
    }

}
