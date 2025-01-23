import React, { useEffect, useState } from "react";

interface UserProps {
    imgUrl : string
}

const ProfileIcon : React.FC<UserProps> = (props) =>{
    const [imgUrl,setImgUrl] = useState<string>("");

    useEffect(
        () => {
            // const defaultIcon = import.meta.env.VITE_ICON_PROFILE;
            const defaultIcon = "https://t4.ftcdn.net/jpg/06/21/45/13/360_F_621451391_mlhtAfqASheIK0UdX2JJhsIXcUHq4zmS.jpg";
            const userImg = props.imgUrl;
            
            // setImgUrl(import.meta.env.VITE_IMG_PATH + ( userImg || defaultIcon ));
            setImgUrl((userImg.trim() != "" ? userImg : defaultIcon ));
        }
    ,[props.imgUrl]);

    return(
        <img src={imgUrl} loading="lazy" className="profile-icon icon" alt="profile icon" />
    );
}

export default ProfileIcon;