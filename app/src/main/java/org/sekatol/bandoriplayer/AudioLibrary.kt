package org.sekatol.bandoriplayer

import androidx.annotation.RawRes

data class AudioBookmark(
    val id: String,
    val label: String,
    val positionMs: Long
)

data class AudioTrack(
    val id: String,
    @RawRes val resId: Int,
    val title: String,
    val bookmarks: List<AudioBookmark>
)

object AudioLibrary {
    val tracks: List<AudioTrack> = listOf(
        AudioTrack(
            id = "h_ah",
            resId = R.raw.h_ah,
            title = "喝 饿啊",
            bookmarks = listOf(
                AudioBookmark("h", "喝", 0L),
                AudioBookmark("h", "饿啊", 311L),
            )
        ),
        AudioTrack(
            id = "bing_bing_bing",
            resId = R.raw.bing_bing_bing,
            title = "冰冰冰",
            bookmarks = listOf(
                AudioBookmark("bing_bing_bing", "冰冰冰", 0L),
            )
        ),
        AudioTrack(
            id = "e_a",
            resId = R.raw.e_a,
            title = "诶啊",
            bookmarks = listOf(
                AudioBookmark("e", "诶", 0L),
                AudioBookmark("a", "啊", 308L),
            )
        ),
        AudioTrack(
            id = "gugugaga",
            resId = R.raw.gugugaga,
            title = "gugugaga",
            bookmarks = listOf(
                AudioBookmark("1", "gu→gu→ga↘ga↘", 0L),
                AudioBookmark("2", "gu↗gu→ga→ga↘", 1925L),
                AudioBookmark("3", "gu↑gu↑ga↗ga↗", 4010L),
                AudioBookmark("4", "喝啊", 6271L),
            )
        ),
        AudioTrack(
            id = "hey",
            resId = R.raw.hey,
            title = "hey",
            bookmarks = listOf(
                AudioBookmark("hey", "hey", 0L),
            )
        ),
        AudioTrack(
            id = "ni_shi_cou_qi_e",
            resId = R.raw.ni_shi_cou_qi_e,
            title = "你是凑企鹅",
            bookmarks = listOf(
                AudioBookmark("ni_shi", "你是", 0L),
                AudioBookmark("cou_qi_e", "凑企鹅", 1560L),
            )
        ),
        AudioTrack(
            id = "ni_shi_she_ya",
            resId = R.raw.ni_shi_she_ya,
            title = "你是谁呀",
            bookmarks = listOf(
                AudioBookmark("ni_shi_she_ya", "你是谁呀", 0L),
            )
        ),
        AudioTrack(
            id = "tomori_ku",
            resId = R.raw.tomori_ku,
            title = "凑企鹅哭",
            bookmarks = listOf(
                AudioBookmark("tomori_ku", "凑企鹅哭", 0L)
            )
        ),
        AudioTrack(
            id = "tomori_xiao",
            resId = R.raw.tomori_xiao,
            title = "凑企鹅笑",
            bookmarks = listOf(
                AudioBookmark("tomori_xiao", "凑企鹅笑", 0L)
            )
        ),
        AudioTrack(
            id = "wo_cong_nan_ji_lai",
            resId = R.raw.wo_cong_nan_ji_lai,
            title = "我从南极来",
            bookmarks = listOf(
                AudioBookmark("wo_cong_nan_ji_lai", "我从南极来", 0L),
                AudioBookmark("zui_xi_huan_chi_shi", "最喜欢持石", 2673L),
                AudioBookmark("ling_gan_gu_gua_ca", "灵感菇刮擦", 5189L),
                AudioBookmark("gu_gu_you_ga_ga", "咕咕又嘎嘎", 7625L),
                AudioBookmark("zu_le_ge_yue_dui", "组了个乐队", 9824L),
                AudioBookmark("quan_dou_bu_shi_ren", "拳斗部诗人", 12469L),
                AudioBookmark("xiang_cheng_wei_ren_lei", "想成为人类", 14865L),
                AudioBookmark("shen_ren_ba_wo_hei", "神人把我黑", 17285L),
            )
        ),
    )

    fun find(id: String): AudioTrack? = tracks.firstOrNull { it.id == id }
}
