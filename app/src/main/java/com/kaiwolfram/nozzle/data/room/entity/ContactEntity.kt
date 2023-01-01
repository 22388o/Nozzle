package com.kaiwolfram.nozzle.data.room.entity

import androidx.room.Entity
import com.kaiwolfram.nostrclientkt.ContactListEntry

// TODO: Remove petname, it's useless
@Entity(tableName = "contact", primaryKeys = ["pubkey", "contactPubkey"])
data class ContactEntity(
    val pubkey: String,
    val contactPubkey: String,
    val relayUrl: String,
    val petname: String,
    val createdAt: Long,
) {
    fun toContactListEntry(): ContactListEntry {
        return ContactListEntry(pubkey = contactPubkey, relayUrl = relayUrl, petname = petname)
    }
}
