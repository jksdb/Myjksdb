package com.yd.ychat.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.yd.ychat.R;
import com.yd.ychat.model.FaceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yinm_pc on 2017/6/6.
 */

public class StringUtil {
    static HashMap<String, Integer> emojiMap = new HashMap();

    /**
     * 初始化数据源
     */
    public static void list2Map() {
        emojiMap.put("[wx]", R.mipmap.ic_launcher);
    }

    /**
     * 数据源类型转换
     * @return
     */
    public static ArrayList<FaceBean> getList() {
        ArrayList<FaceBean> list = new ArrayList<>();
        Set<String> set = emojiMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            list.add(new FaceBean(name, emojiMap.get(name)));
        }
        return list;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     *
     * @param context  用于 ImageSpan 设置图片id时
     * @param str      需要进行匹配的字符串
     * @return         匹配并替换成图片后的字符串
     */
    public static SpannableString getExpressionString(Context context, String str) {

        SpannableString spannableString = new SpannableString(str);

        // 正则表达式比配字符串里是否含有表情 [xxx]
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context           用于 ImageSpan 设置图片id时
     * @param spannableString   需要进行匹配的字符串
     * @param patten            正则模型
     * @param start             匹配的字符 开始下标 不能小于此值
     * @throws Exception
     */
    private static void dealExpression(Context context,
                                       SpannableString spannableString, Pattern patten, int start)
            throws Exception {

        Matcher matcher = patten.matcher(spannableString);

        while (matcher.find()) {
            // 返回匹配的结果
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }

            int resId = emojiMap.get(key);

            if (TextUtils.isEmpty(resId + "")) {
                continue;
            }
            if (resId != 0) {
                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                ImageSpan imageSpan = new ImageSpan(context, resId);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int end = matcher.start() + key.length();
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(imageSpan, matcher.start(), end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    // 如果整个字符串还未验证完，则继续。。
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }

    }
}
