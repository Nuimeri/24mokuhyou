import React, { useEffect, useState } from "react";
import { fetchReceivedInvitations } from "../../api/fetchReceivedInvitations";
import { Invitation, Team } from "../../Types";
import { fetchTeamData } from "../../api/fetchTeamData ";
import { Link } from "react-router-dom";
import { updateAcceptInvitation } from "../../api/updateAcceptInvitation";
import { updateRejectInvitation } from "../../api/updateRejectInvitation";

const NoticeInvite : React.FC = () => {
    const [invites,setInvites] = useState<Invitation[]>([]);
    const [teams,setTeams] = useState<Team[]>([]);

    useEffect(() => {
        const fetchInvite = async () => {
            // 招待を取得
            const response = await fetchReceivedInvitations();
            setInvites(response);
        };

        fetchInvite();
    },[]);

    useEffect(() => {
         // teamcodeからチーム情報を取得
        const fetchTeam = async (teamcode : string) => {
            const response = await fetchTeamData(teamcode);
            
            // Teamsにkeyが重複していない場合のみ追加
            if(!teams.some((team) => team.code === response.code)){
                setTeams((prevTeams) => [...prevTeams,response]);
            }
        }; 
        
        const fetchTeams = async () => {
            const promises = invites.map((invite) => fetchTeam(invite.teamcode));
            await Promise.all(promises);
        };

        fetchTeams();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    },[invites]);

    // 承認ボタン
    const handleAccept = async (ticketCode : string) => {
        const response = await updateAcceptInvitation(ticketCode);
        if(response){
            setInvites((prevInvites) => prevInvites.filter((prevInvite) => prevInvite.code !== ticketCode));
        }else{
            alert("承認に失敗しました");
        }
    };

    // 拒否ボタン
    const handleReject = async (ticketCode : string) => {
        const response = await updateRejectInvitation(ticketCode);
        if(response){
            setInvites((prevInvites) => prevInvites.filter((prevInvite) => prevInvite.code !== ticketCode));
        }else{
            alert("拒否に失敗しました");
        }
    };

    return (
        <div className="notice-invite">
            {invites.length > 0 ? (
                <div>
                    <ul>
                        {invites.map((invite) => (
                            <li key={invite.code}  className="team-row">
                                <div>
                                    {/* <TeamIcon imgUrl={teams.find((team) => team.code === invite.teamcode)?.icon || defaultTeamData.icon} /> */}
                                    <Link to={`/${invite.senderName}`}>{invite.senderName}</Link>
                                    <label>さんから</label>
                                    <Link to={`/teams/${invite.teamcode}`}>{teams.find((team) => team.code === invite.teamcode)?.name || ""}</Link>
                                    <label>への招待が届いています</label>
                                    <button onClick={() => handleAccept(invite.code)}>承認</button>
                                    <button onClick={() => handleReject(invite.code)}>拒否</button>
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            ) : (
                <div className="hidden"></div>
            )}
        </div>
    );
}

export default NoticeInvite;