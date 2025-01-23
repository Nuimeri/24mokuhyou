import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AxiosError } from "axios"; // Axiosのエラー型をインポート
import { postLoginInfo } from "../api/postLoginInfo";
import { MODE_LOGIN } from "../constants/Constants";

// useAuth: 認証処理を行うカスタムフック
export const useAuth = (mode : number, fromUrl : string | null) => {
  // 認証ステータスを管理するための state。初期値はnull
  const [status, setStatus] = useState<number | null>(null);
  
  // ページ遷移を行うための React Router の navigate フック
  const navigate = useNavigate();

  // login: ログイン処理を行う非同期関数
  const login = async (username: string, password: string) => {
    try {
      // 認証APIを呼び出してユーザーを認証
      const response = await postLoginInfo(username, password);
      // 通常ログインならログイン前の閲覧ページへ遷移、またはユーザーホーム
      const toUrl : string = fromUrl || username;

      // ステータスコードを更新
      setStatus(response.status);
      if(mode == MODE_LOGIN){
        navigate("/" + toUrl); 
      }else{
        // 新規登録の途中なら edit ページへ遷移
        navigate("/" + username + "/edit?from=" + fromUrl);
      }
    } catch (error : unknown) {
      alert("ログインに失敗しました");

      // エラーが発生した場合の処理
      if (error instanceof AxiosError) {
        // AxiosError ならば、エラーのステータスコードを設定
        setStatus(error.response?.status || 500);
      } else {
        // 予期しないエラーの場合は 500 ステータスを設定
        setStatus(500);
      }
      // エラー発生時は /login ページへリダイレクト
      navigate("/login");
    }
  };

  // ステータスと login 関数を返す
  return { status, login };
};
