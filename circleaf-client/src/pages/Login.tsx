import React from "react";
import { useAuth } from "../hooks/useAuth";
import LoginForm from "../component/LoginForm";
import { MODE_LOGIN } from "../constants/Constants";
import { useSearchParams } from "react-router-dom";

const Login: React.FC = () => {
  const [searchParams] = useSearchParams();
  const fromUrl : string | null = searchParams.get("from");
  const { login } = useAuth(MODE_LOGIN,fromUrl);

  return (
    <div>
      <h1>ログイン</h1>
      <LoginForm onLogin={login} />
    </div>
  );
};

export default Login;
