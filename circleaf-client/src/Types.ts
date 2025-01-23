
export type ProfileData = { 
    username : string 
    , nickname : string
    , description : string
    , icon : string
    , birthday : Date | null
    , createAt : Date
 };

 export type Team = {
    code : string
    ,name : string
    ,category : string
    ,founder : string
    ,leader : string
    ,icon : string
    ,music : string
    ,description : string
    ,createAt : Date
    ,createBy : string
 }

 export type Post = {
   code : string
   ,accountId : string
   ,postText : string
   ,image :  string
   ,createAt : Date
   ,updateAt : Date

   // DBにはない
   ,username : string
 }

 export type SignupData = {
   mail : string
   ,password : string
   ,birthday : Date
 }

 export type Invitation = {
    code : string
    ,teamcode : string
    ,senderName : string
    ,recipientName : string
    ,createAt : Date
    ,updateAt : Date
  }