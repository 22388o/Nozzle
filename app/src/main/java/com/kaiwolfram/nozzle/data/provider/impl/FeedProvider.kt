package com.kaiwolfram.nozzle.data.provider.impl

import android.util.Log
import com.kaiwolfram.nozzle.data.defaultPubkeys
import com.kaiwolfram.nozzle.data.provider.IFeedProvider
import com.kaiwolfram.nozzle.data.provider.IInteractionStatsProvider
import com.kaiwolfram.nozzle.data.provider.IPostProvider
import com.kaiwolfram.nozzle.data.provider.IPubkeyProvider
import com.kaiwolfram.nozzle.data.room.dao.ContactDao
import com.kaiwolfram.nozzle.data.room.dao.PostDao
import com.kaiwolfram.nozzle.model.PostWithMeta

private const val TAG = "FeedProvider"

class FeedProvider(
    private val pubkeyProvider: IPubkeyProvider,
    private val postProvider: IPostProvider,
    private val interactionStatsProvider: IInteractionStatsProvider,
    private val postDao: PostDao,
    private val contactDao: ContactDao
) : IFeedProvider {

    override suspend fun getFeed(): List<PostWithMeta> {
        Log.i(TAG, "Get feed")
        val followingCount = contactDao.getNumberOfFollowing(pubkey = pubkeyProvider.getPubkey())
        val posts = if (followingCount > 0) {
            postDao.getLatestFeed(pubkey = pubkeyProvider.getPubkey())
        } else {
            Log.i(TAG, "Use default contacts")
            postDao.getLatestFeedOfCustomContacts(contactPubkeys = defaultPubkeys)
        }
        val stats = interactionStatsProvider.getStats(posts.map { it.id })
        val reposts = postProvider.getRepostsMap(posts.mapNotNull { it.repostedId })

        return posts.map {
            PostWithMeta(
                id = it.id,
                replyToId = it.replyTo,
                replyToRootId = it.replyToRoot,
                pubkey = it.pubkey,
                createdAt = it.createdAt,
                content = it.content,
                // TODO: real values
                // profile related data from dao map
                name = "TODO",
                pictureUrl = "https://64.media.tumblr.com/a727acf2c19888056b03500a89227cd4/0f1f0b7b20b511df-c9/s400x600/afeb2ab1cf61c2e4e93b6fba00c983a6a8cb9d60.gifv",
                replyToName = "TODO",
                repost = it.repostedId?.let { repostedId -> reposts[repostedId] },
                isLikedByMe = stats.isLikedByMe(it.id),
                isRepostedByMe = stats.isRepostedByMe(it.id),
                numOfLikes = stats.getNumOfLikes(it.id),
                numOfReposts = stats.getNumOfReposts(it.id),
                numOfReplies = stats.getNumOfReplies(it.id),
            )
        }
    }

    override suspend fun getFeedSince(since: Long): List<PostWithMeta> {
        Log.i(TAG, "Fetch feed since $since")
        val followingCount = contactDao.getNumberOfFollowing(pubkey = pubkeyProvider.getPubkey())
        val posts = if (followingCount == 0) {
            postDao.getFeedSince(pubkey = pubkeyProvider.getPubkey(), since = since)
        } else {
            Log.i(TAG, "Use default contacts")
            postDao.getFeedOfCustomContactsSince(contactPubkeys = defaultPubkeys, since = since)
        }
        val stats = interactionStatsProvider.getStats(posts.map { it.id })
        val reposts = postProvider.getRepostsMap(posts.mapNotNull { it.repostedId })

        return posts.map {
            PostWithMeta(
                id = it.id,
                replyToId = it.replyTo,
                replyToRootId = it.replyToRoot,
                pubkey = it.pubkey,
                createdAt = it.createdAt,
                content = it.content,
                replyToName = "TODO",
                name = "TODO",
                pictureUrl = "https://64.media.tumblr.com/a727acf2c19888056b03500a89227cd4/0f1f0b7b20b511df-c9/s400x600/afeb2ab1cf61c2e4e93b6fba00c983a6a8cb9d60.gifv",
                repost = it.repostedId?.let { repostedId -> reposts[repostedId] },
                isLikedByMe = stats.isLikedByMe(it.id),
                isRepostedByMe = stats.isRepostedByMe(it.id),
                numOfLikes = stats.getNumOfLikes(it.id),
                numOfReposts = stats.getNumOfReposts(it.id),
                numOfReplies = stats.getNumOfReplies(it.id),
            )
        }
    }

    override suspend fun getLatestTimestamp(): Long? {
        return postDao.getLatestTimestampOfFeed(pubkey = pubkeyProvider.getPubkey())
    }

    override suspend fun getFeedWithSingleAuthor(pubkey: String): List<PostWithMeta> {
        TODO("Not yet implemented")
    }

}
