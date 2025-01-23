import React from "react";
import SiteLogo from "./SiteLogo";
import { Link } from "react-router-dom";
import "../assets/style/RequireLogin.css"

interface RequireLoginProps {
    fromUrl : string
}

const RequireLogin : React.FC<RequireLoginProps> = (props) => {
    return (
        <div className="require-login">
            <h1 className="site-name">Circleaf</h1>
            <span className="need-login">ログインが必要です</span>
            <SiteLogo />
            <div className="urls">
                <Link to={"/login?from=" + props.fromUrl} className="btn">ログインする</Link>
                <Link to={"/signup?from=" + props.fromUrl} className="btn">新規登録</Link>
            </div>
        </div>
    );
}

export default RequireLogin;