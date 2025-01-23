import React, { useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { insertAccount } from "../api/insertAccount";
import { useAuth } from "../hooks/useAuth";
import { MODE_SIGN_UP } from "../constants/Constants";

interface SignupData {
  mail: string;
  password: string;
  birthday: Date;
}

const SignUp: React.FC = () => {
  const [formState, setFormState] = useState<SignupData>({
    mail: "",
    password: "",
    birthday: new Date(),
  });
  const [searchParams] = useSearchParams();
  const fromUrl : string = searchParams.get("from") || "/";
  const { login } = useAuth(MODE_SIGN_UP,fromUrl);

  // フィールドを編集したときのハンドラー
  const handleChange = (key: keyof SignupData, value: string) => {
    setFormState((prevState) => ({
      ...prevState,
      [key]: key === "birthday" ? new Date(value) : value,
    }));
  };

  // Form送信時処理
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const insertAccountRes: string = await insertAccount(formState);

      if (insertAccountRes) {
        alert("プロフィール登録に進みます");
        // ログインしプロフィール編集へ遷移
        await login(insertAccountRes, formState.password);
        } else {
        alert("アカウント登録に失敗しました");
      }
    } catch (error) {
      alert("Error");
      console.error("Error insert account: " + error);
    }
  };

  return (
    <div>
      <div>
        <Link to={"/home"}>戻る</Link>
      </div>
      <form onSubmit={handleSubmit}>
        <ul>
          <li>
            <strong>メールアドレス</strong>
            <input
              type="text"
              value={formState.mail}
              onChange={(e) => handleChange("mail", e.target.value)}
              className="signup-input"
            />
          </li>
          <li>
            <strong>パスワード</strong>
            <input
              type="password"
              value={formState.password}
              onChange={(e) => handleChange("password", e.target.value)}
              className="signup-input"
            />
          </li>
          <li>
            <strong>生年月日</strong>
            <input
                type="date"
                value={formState.birthday.toISOString().split("T")[0]} 
                onChange={(e) => handleChange("birthday", e.target.value)}
                className="signup-input"
            />
          </li>
        </ul>
        <button type="submit">登録</button>
      </form>
    </div>
  );
};

export default SignUp;
