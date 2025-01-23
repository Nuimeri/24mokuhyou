import React, { useState, useEffect } from "react";
import { Post, ProfileData } from "../../Types";
import { defaultPostData, defaultProfileData } from "../../constants/DefaultParameter";
import { fetchPostData } from "../../api/fetchPostData";
import { fetchProfileData } from "../../api/fetchProfileData";
import ProfileIcon from "../../component/ProfileIcon";

interface PostDetailProps {
    postcode: string;
}

const PostDetail: React.FC<PostDetailProps> = ({ postcode }) => {
    const [postData, setPostData] = useState<Post | null>(defaultPostData); // postDataがnullを許容
    const [profile, setProfile] = useState<ProfileData>(defaultProfileData);

    useEffect(() => {
        const fetchData = async () => {
            // postcodeの指定があればpostの情報を取得
            if(postcode.trim() != ""){
                const postDataRes = await fetchPostData(postcode);
                const profileData = await fetchProfileData(postDataRes.username);
                
                setPostData(postDataRes);
                setProfile(profileData);
            }
        };

        fetchData();
    }, [postcode]); // postcode が変更されるたびにデータを再取得

    // postDataが取得できない場合
    if (!postData || postData.code.trim() == "") {
        return (
            <div className="nothing-post">
                まだ投稿がありません
            </div>
        );
    }

    return (
        <div className="post-detail">
            <ProfileIcon imgUrl={profile.icon} />
            <div className="username">
                <label>{profile ? profile.username : ""}</label>
            </div>
            <div className="nickname">
                <label>{profile ? profile.nickname : ""}</label>
            </div>
            <div className="post-text">
                <strong>{postData.postText}</strong>
            </div>
        </div>
    );
};

export default PostDetail;
