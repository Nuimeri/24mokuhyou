import React, { useEffect, useState } from "react";
import { Team } from "../../Types";
import { fetchJoinTeam } from "../../api/fetchJoinTeams";
import TeamIcon from "../../component/TeamIcon";
import { Link } from "react-router-dom";
import { fetchLoginStatus } from "../../api/fetchLoginStatus";

interface TeamProps {
    isMine : boolean
    ,username : string
    ,atProfile : boolean
    ,teamname?: string
    ,teamcode?: string
    ,category?: string
}

const TeamList : React.FC<TeamProps> = (props) =>{
    const [username, setUsername] = useState<string>(props.username);
    const [mode, setMode] = useState<boolean>(props.atProfile); // true : 「新規チーム作成」を表示
    const [teams,setTeams] = useState<Team[]>([]);
    // const [teamname,setTeamName] = useState<string>("");
    // const [teamcode,setTeamcode] = useState<string>("");
    // const [category,setCategory] = useState<string>("");
    const [teamname] = useState<string>("");
    const [teamcode] = useState<string>("");
    const [category] = useState<string>("");
    const [Loading,setLoading] = useState<boolean>(true);
    const [error,setError] = useState<string | null>(null);

    useEffect(() => {
            const fetchTeams = async () => {
                try {
                    const loginRes = await fetchLoginStatus();

                    if(username.trim() == ""){
                    // usernameの指定がなければログイン中ユーザを指定する    
                        setUsername(loginRes || "");
                        setMode(false);
                    }else{
                        const resTeams = await fetchJoinTeam(username);
                        setTeams(resTeams);
                        setLoading(false);
                    }
                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                } catch (error : any) {
                    setError(error.message);
                    setLoading(false);                  
                }
            };
            fetchTeams();
        }, [username,teamname,teamcode,category]);

    if(Loading){
        return(
            <div>
                Loading...
            </div>
        );
    }

    if(error){
        return(
            <div>
                Error : {error}
            </div>
        );
    }

    return(
        <div className="team-list">
            { !mode && 
                <Link to={"/teams/new"}>新規チーム作成</Link>
            }
            <ul>
                { teams.map( team => (
                    <li key={team.code} 
                        onClick={() => {
                            window.location.pathname = "../teams/" + team.code; 
                            }}
                        className="team-row"
                    >
                        <div>
                            <Link to={"/teams/" + team.code}>
                                <TeamIcon imgUrl={team.icon} />
                            </Link>
                        </div>
                        <div className="team-info">
                            <div className="team-names">
                                <span className="name">
                                    <span className="team-name">
                                        {team.name}
                                    </span>
                                    <span className="team-code">
                                        @{team.code}
                                    </span>
                                </span>
                                <span className="category">
                                    {team.category}
                                </span>
                            </div>
                            <div className="description">
                                {team.description}
                            </div>
                        </div>
                    </li> 
                    ))
                }
            </ul>
        </div>
    );
}

export default TeamList;