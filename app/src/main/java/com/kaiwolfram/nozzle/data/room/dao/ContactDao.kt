package com.kaiwolfram.nozzle.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.kaiwolfram.nozzle.data.room.entity.ContactEntity

// TODO: Prevent duplicate primary key constraint in all Inserts

@Dao
interface ContactDao {

    @Query(
        "SELECT contactPubkey " +
                "FROM contact " +
                "WHERE pubkey = :pubkey"
    )
    suspend fun listContactPubkeys(pubkey: String): List<String>

    @Query(
        "SELECT * " +
                "FROM contact " +
                "WHERE pubkey = :pubkey"
    )
    suspend fun listContacts(pubkey: String): List<ContactEntity>

    @Query(
        "INSERT INTO contact (pubkey, contactPubkey, createdAt) " +
                "VALUES (:pubkey, :contactPubkeys, :createdAt)"
    )
    fun insert(pubkey: String, contactPubkeys: List<String>, createdAt: Long)

    @Query(
        "INSERT INTO contact (pubkey, contactPubkey, relayUrl, petname, createdAt) " +
                "VALUES (:pubkey, :contactPubkey, :relayUrl, :petname, :createdAt)"
    )
    fun insert(
        pubkey: String,
        contactPubkey: String,
        relayUrl: String,
        petname: String,
        createdAt: Long,
    )

    @Query(
        "DELETE FROM contact " +
                "WHERE pubkey = :pubkey AND contactPubkey = :contactPubkey"
    )
    suspend fun deleteContact(pubkey: String, contactPubkey: String)

    @Query(
        "UPDATE contact " +
                "SET createdAt = :createdAt " +
                "WHERE pubkey = :pubkey"
    )
    suspend fun updateTime(pubkey: String, createdAt: Long)

    @Query(
        "SELECT COUNT(*) " +
                "FROM contact " +
                "WHERE pubkey = :pubkey"
    )
    suspend fun getNumberOfFollowing(pubkey: String): Int

    @Query(
        "SELECT COUNT(*) " +
                "FROM contact " +
                "WHERE contactPubkey = :pubkey"
    )
    suspend fun getNumberOfFollowers(pubkey: String): Int

    @Query(
        "SELECT EXISTS(SELECT * " +
                "FROM contact " +
                "WHERE pubkey = :pubkey AND contactPubkey = :contactPubkey)"
    )
    suspend fun isFollowed(pubkey: String, contactPubkey: String): Boolean

    @Query(
        "DELETE FROM contact " +
                "WHERE pubkey = :pubkey AND createdAt < :createdAt"
    )
    fun deleteIfOutdated(pubkey: String, createdAt: Long)
}
