import React, { useEffect, useState, useRef } from "react";
import { ProfileData } from "../../Types";
import { Link, useNavigate, useParams, useSearchParams } from "react-router-dom";
import { fetchProfileData } from "../../api/fetchProfileData";
import ProfileIcon from "../../component/ProfileIcon";
import { defaultProfileData } from "../../constants/DefaultParameter";
import { updateProfileData } from "../../api/updateProfileData";

const ProfileEdit: React.FC = () => {
    const { username: routeUsername } = useParams<{ username: string }>();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const fromUrl : string | null = searchParams.get("from");

    const [username] = useState(routeUsername || "");
    const [profileData, setProfileData] = useState<ProfileData>(defaultProfileData);
    const [iconImg, setIconImg] = useState<string>(defaultProfileData.icon);
    const fileInputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        const fetchData = async () => {
            if (username) {
                const profileDataRes = await fetchProfileData(username);
                setProfileData(profileDataRes);
                setIconImg(profileDataRes.icon);
            }
        };

        fetchData();
    }, [username]);

    // フィールドを編集したときのハンドラー
    const handleChange = (key: string, value: string | number) => {
        setProfileData((prev) => ({
            ...prev,
            [key]: value,
        }));
    };

    // プロフィール画像が選択されたときのハンドラー（画像プレビュー）
    const handleIconChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();

        if (e.currentTarget?.files && e.currentTarget.files[0]) {
            const targetFile = e.currentTarget.files[0];
            const reader = new FileReader();

            reader.onloadend = () => {
                setIconImg(reader.result as string);
            };

            reader.readAsDataURL(targetFile);
        }
    };

    // Form送信時処理
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const profileUpdateRes = await updateProfileData(username, profileData);
            if (profileUpdateRes === 1) {
                alert("プロフィール更新完了");
                if(fromUrl){
                    navigate("/" + fromUrl);
                }else{
                    navigate(`/${profileData.username}`);
                }
            } else {
                alert("プロフィール更新に失敗しました");
            }
        } catch (error) {
            alert("Error");
            console.error("Error updating profile:", error);
        }
    };

    return (
        <div>
            {profileData ? "" : "Loading..."}
            <div>
                <Link to={`/${username}`}>ホームに戻る</Link>
            </div>
            <form onSubmit={handleSubmit}>
                <div onClick={() => fileInputRef.current?.click()}>
                    <ProfileIcon imgUrl={iconImg} />
                </div>
                <input
                    type="file"
                    accept="image/*"
                    name="iconImg"
                    ref={fileInputRef}
                    onChange={handleIconChange}
                    hidden
                />
                <ul>
                    <li>
                        <strong>ID</strong>
                        <input
                            type="text"
                            value={profileData.username.toString()}
                            onChange={(e) => handleChange("username", e.target.value)}
                            className="profile-edit-input"
                        />
                    </li>
                    <li>
                        <strong>ニックネーム</strong>
                        <input
                            type="text"
                            value={profileData.nickname.toString()}
                            onChange={(e) => handleChange("nickname", e.target.value)}
                            className="profile-edit-input"
                        />
                    </li>
                    <li>
                        <strong>誕生日</strong>
                        <input
                            type="text"
                            value={
                                profileData.birthday instanceof Date
                                    ? profileData.birthday.toLocaleDateString("ja-JP", {
                                          year: "numeric",
                                          month: "2-digit",
                                          day: "2-digit",
                                      })
                                    : typeof profileData.birthday === "string"
                                    ? new Date(profileData.birthday).toLocaleDateString("ja-JP")
                                    : ""
                            }
                            onChange={(e) => handleChange("birthday", e.target.value)}
                            className="profile-edit-input"
                        />
                    </li>
                    <li>
                        <strong>自己紹介</strong>
                        <textarea
                            value={profileData.description.toString()}
                            onChange={(e) => handleChange("description", e.target.value)}
                            className="profile-edit-input"
                        />
                    </li>
                </ul>
                <button type="submit">登録</button>
            </form>
        </div>
    );
};

export default ProfileEdit;
