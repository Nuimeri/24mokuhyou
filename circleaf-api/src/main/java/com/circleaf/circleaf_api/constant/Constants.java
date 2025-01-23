package com.circleaf.circleaf_api.constant;

import java.util.List;
import java.util.Arrays;

public class Constants {

    /* ログインユーザ確認用 */
    public static final int IS_SAME_USER = 1;
    public static final int IS_DIFFERENT_USER = -1;

    /* 汎用判定結果(int) */
    public static final int IS_OK_INT = 1;
    public static final int IS_ERR_INT = -1;

    /* 招待ステータス */
    public static final int INVITATION_UNAPPROVED = 0;    // 未承認
    public static final int INVITATION_APPROVED = 1;  // 承認
    public static final int INVITATION_REJECTED = 2;  // 拒否
    public static final int INVITATION_CANCELED = 3;  // キャンセル

    /* バリデーション結果（int） */
    // 存在しないユーザー
    public static final int IS_NOT_EXIST_USER = -1;
    // 既に存在するユーザー
    public static final int IS_EXIST_USER = -2;
    // 既に招待済み
    public static final int IS_ALREADY_INVITED = -3;
    // 存在しないチーム
    public static final int IS_NOT_EXIST_TEAM = -4;
    // 既に存在するチーム
    public static final int IS_EXIST_TEAM = -5;


    /* APIにアクセスを許可するOrigin */
    public static final List<String> ALLOWED_ORIGINS = Arrays.asList(
        "http://localhost:5173"
        ,"http://localhost:8000"
        ,"http://localhost:8080"
        ,"http://localhost:81"
    );
}
