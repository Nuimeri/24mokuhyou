import React, { useEffect, useState } from "react";
import { fetchLoginStatus } from "../../api/fetchLoginStatus";
import TeamList from "../profiles/TeamList";
import "../../assets/style/FindTeam.css"
import RequireLogin from "../../component/RequireLogin";
import { Link } from "react-router-dom";
import NoticeInvite from "../notice/NoticeInvite";

interface FindTeamProps {
    isMine : boolean
    ,teamname?: string
    ,teamcode?: string
    ,category?: string
}


const FindTeam : React.FC<FindTeamProps> = (props) => {
    const [username,setUsername] = useState<string | null>(null);
    // const [teamname,setTeamName] = useState<string>("");
    // const [teamcode,setTeamcode] = useState<string>("");
    // const [category,setCategory] = useState<string>("");
    const [teamname] = useState<string>("");
    const [teamcode] = useState<string>("");
    const [category] = useState<string>("");
    const [isLoaded,setIsLoaded] = useState<boolean>(false);

    useEffect(()=>{
        const fetchData = async () => {
            const loginStatusRes = await fetchLoginStatus();
            setUsername(loginStatusRes);
            setIsLoaded(true);
        }

        fetchData();
    },[teamname,teamcode,category]);

    if(!isLoaded){
        // ロード中
        return(
            <div>
                Loading...
            </div>
        );
    }else if(props.isMine && !username){
        // ログインしていない場合
        return(
            <RequireLogin fromUrl="teams" />
        );
    }

    return (
        <div className="find-team">
            {props.isMine ? 
                <div className="notice-invite">
                    <NoticeInvite />
                </div>    
                :
                <div className="find-box">
                    検索ボックス
                </div>
            }
            <div className="find-result">
                <TeamList 
                    username={username || " "} 
                    atProfile={false} 
                    isMine={true}
                    teamname={teamname}
                    teamcode={teamcode}
                    category={category}
                />
            </div>
        </div>
    );
}

export default FindTeam;