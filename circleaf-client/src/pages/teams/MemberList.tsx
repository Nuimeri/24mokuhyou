import React, { useEffect, useState } from "react";
import { ProfileData } from "../../Types";
import { Link } from "react-router-dom";
import ProfileIcon from '../../component/ProfileIcon';
import { fetchJoinMember } from "../../api/fetchJoinMembers";

interface MemberProps {
    teamcode : string
}

const MemberList : React.FC<MemberProps> = (props) =>{
    const [members,setMembers] = useState<ProfileData[]>([]);
    const [Loading,setLoading] = useState<boolean>(true);
    const [error,setError] = useState<string | null>(null);

    useEffect(
        () => {
            const fetchMembers = async () => {
                try {
                    const resMembers = await fetchJoinMember(props.teamcode);
                    setMembers(resMembers);
                    setLoading(false);
                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                } catch (error : any) {
                    setError(error.message);
                    setLoading(false);                  
                }
            };
            fetchMembers();
        }
    , [props.teamcode]);

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
        <ul className="member-list">
            { members.map( member => (
                <li key={member.username} 
                    onClick={() => {
                       window.location.pathname = "../" + member.username; 
                    }}
                >
                    <div>
                        <Link to={"../" + member.username}>
                            <ProfileIcon imgUrl={member.icon} />
                        </Link>
                    </div>
                    <div>
                        {member.nickname}
                    </div>
                </li> 
                ))
            }
        </ul>
    );
}

export default MemberList;