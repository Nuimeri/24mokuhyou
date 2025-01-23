import React, { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { postLogout } from "../api/postLogout";
import { LOGOUT_SUCCESS } from "../constants/Constants";

const LogoutLink: React.FC = () => {
    const navigate = useNavigate();

    const confirmAndLogout = useCallback(async (event: React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault(); // リンクのデフォルトの遷移を防ぐ

        const isConfirmed = window.confirm("ログアウトしますか？");
        if (isConfirmed) {
            try {
                const hasLogout = await postLogout(); // ログアウトリクエスト
                if (hasLogout.status == LOGOUT_SUCCESS) {
                    alert("サイトトップにスキップします")
                    navigate("/"); // トップページにリダイレクト
                } else {
                    console.error("ログアウトに失敗しました。");
                }
            } catch (error) {
                console.error("エラーが発生しました:", error);
            }
        }
    }, [navigate]);

    return (
        <a href="/" onClick={confirmAndLogout} className="logout-link">
            ログアウト
        </a>
    );
};

export default LogoutLink;
