package com.skyd.rays.util.share.app

import android.content.Context
import android.net.Uri
import com.skyd.rays.util.share.ShareUtil

class DiscordAppInfo : IAppInfo {
    override val packageName: String
        get() = "com.discord"

    override fun share(context: Context, topActivityFullName: String, uri: Uri): Boolean {
        if (!topActivityFullName.startsWith(packageName)) return false
        ShareUtil.startShare(
            context = context,
            uri = uri,
            packageName = packageName,
            className = "com.discord.share.ShareActivity"
        )
        return true
    }
}