import { Post, ProfileData, Team } from "../Types";

export const defaultProfileData : ProfileData = {
    username : "unknown"
    , nickname : "unknown"
    , description : ""
    , icon : "https://t4.ftcdn.net/jpg/06/21/45/13/360_F_621451391_mlhtAfqASheIK0UdX2JJhsIXcUHq4zmS.jpg"
    // , icon : import.meta.env.VITE_ICON_PROFILE
    , birthday : null
    , createAt : new Date()
}

export const defaultTeamData : Team = {
    code : " "
    ,name : " "
    ,category : " "
    ,founder : " "
    ,leader : " "
    ,icon : import.meta.env.VITE_ICON_TEAM
    ,music : " "
    ,description : " "
    ,createAt : new Date()
    ,createBy : " "
}

export const defaultPostData : Post = {
   code : " "
   ,accountId : " "
   ,postText : " "
   ,image :  " "
   ,createAt : new Date()
   ,updateAt : new Date()
   ,username : " "
}