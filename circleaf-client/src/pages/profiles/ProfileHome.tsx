import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { ProfileData } from "../../Types";
import { fetchProfileData } from "../../api/fetchProfileData";
import ProfileIcon from "../../component/ProfileIcon";
import TeamList from "./TeamList";
import { defaultProfileData } from "../../constants/DefaultParameter";
import PostDetail from "../posts/PostDetail";
import { fetchAttentionPostCode } from "../../api/fetchAttentionPostCode";
import "../../assets/style/ProfileHome.css";
import { fetchLoginStatus } from "../../api/fetchLoginStatus";

const ProfileHome: React.FC = () => {
    // URLパラメータからusernameを取得
    const { username: usernameParam } = useParams<{ username: string }>();
    const username = usernameParam ?? "";  // null または undefined の場合、空文字を代入

    const [profileData, setProfileData] = useState<ProfileData>(defaultProfileData);
    const [postcode, setPostcode] = useState<string>("");
    const [loginUser,setLoginUser] = useState<string | null>(null);
    const [isLoaded, setIsLoaded] = useState<boolean>(false);

    useEffect(() => {
        const fetchData = async () => {
            const profileDataRes = await fetchProfileData(username);
            const postcodeRes = await fetchAttentionPostCode(username);
            const loginUserRes = await fetchLoginStatus();

            setProfileData(profileDataRes);
            setPostcode(postcodeRes);
            setLoginUser(loginUserRes);

            // ロード完了
            setIsLoaded(true);
        };

        fetchData();
    }, [username]);

    if (!isLoaded) {
        // ロード中
        return <div>Loading...</div>;
    } 

    return (
        <div className="profile-home">
            <div>
                {profileData.username !== defaultProfileData.username ? "" : "Loading..."}
            </div>
            <div>
                <ProfileIcon
                    imgUrl={profileData.icon.trim() !== "" ? profileData.icon : defaultProfileData.icon}
                />
            </div>
            <div>
                { loginUser == username ?
                    <Link to={`/${username}/edit`} className="edit-link">
                        編集
                    </Link>
                    : ""    
                }
            </div>
            <div>
                <span className="nickname">
                    {profileData.nickname !== defaultProfileData.nickname ? profileData.nickname : ""}
                </span>
                <span className="username">@{username}</span>
            </div>
            <div className="ff-count">1 follower</div>
            <div className="description">{profileData.description}</div>
            <div className="birthday">
                誕生日:{" "}
                <span className="birthday-date">
                    {profileData.birthday != null?
                        profileData.birthday instanceof Date ? 
                            profileData.birthday.toLocaleDateString("ja-JP", {
                                    year: "numeric",    
                                    month: "2-digit",
                                    day: "2-digit",
                                })
                            : new Date(profileData.birthday).toLocaleDateString("ja-JP")
                        : ""
                    }
                </span>
            </div>
            <div className="ref-web"></div>
            <div className="teams">
                <TeamList username={username} atProfile={true} isMine={true}/>
            </div>
            <div className="posts-link">
                <Link to={`/posts/${username}`}>投稿一覧&gt;&gt;</Link>
            </div>
            <div className="announce-post">
                <PostDetail postcode={postcode} />
            </div>
            <div className="ref-profiles"></div>
        </div>
    );
};

export default ProfileHome;
