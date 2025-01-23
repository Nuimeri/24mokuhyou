import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { fetchLoginStatus } from "../api/fetchLoginStatus";
import ProfileIcon from "../component/ProfileIcon";
import { defaultProfileData } from "../constants/DefaultParameter";
import { ProfileData } from "../Types";
import { fetchProfileData } from "../api/fetchProfileData";
import LogoutLink from "../component/LogoutLink";
import SiteLogo from "../component/SiteLogo";


const Header: React.FC = () => {
    const location = useLocation();
    const [username, setUsername] = useState<string | null>(null);
    const [profileData,setProfileData] = useState<ProfileData>(defaultProfileData);
  
    useEffect(() => {
        const checkLoginStatus = async () => {
            try {
                //   const response = await axios.get<string | null>('/isLogin', { withCredentials: true });
                const username = await fetchLoginStatus();
                const profileData = await fetchProfileData(username || "");
                
                setUsername(username); // ログイン中ならアカウント名、ログインしていなければNULL
                if(username){
                // usernameがNULLでなければ設定
                    setProfileData(profileData);
                }
            } catch (error) {
                console.error('Failed to check login status:', error);
                setUsername(null);
            }
        };
  
        checkLoginStatus();
    }, [location]);
  
    return (
        <header className="color-theme-lite">
            <SiteLogo />
            {username ? (
                <div className="right-link">
                    <span className="logout-link">
                        <LogoutLink />
                    </span>
                    <Link to={"/" + username} className="profile-icon">
                            <ProfileIcon imgUrl={profileData.icon} />
                    </Link>
                </div>
            ) : (
                <div className="right-link">
                    <Link to="/login" className="login-link">ログイン</Link>
                </div>
            )}
        </header>
    );
};

export default Header;