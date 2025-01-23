import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { Team } from "../../Types";
import TeamIcon from "../../component/TeamIcon";
import { defaultProfileData } from "../../constants/DefaultParameter";
import MemberList from "./MemberList";
import "../../assets/style/TeamHome.css";
import "../../assets/style/Modal.css";
import { fetchTeamData } from "../../api/fetchTeamData ";
import { Modal } from "@mui/material";
import { insertInvitation } from "../../api/insertInvitation";

const TeamHome: React.FC = () => {
    const { teamcode } = useParams<{ teamcode: string }>();
    const [team, setTeam] = useState<Team | undefined>(undefined);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [invitedUser, setInvitedUser] = useState<string>("");

    useEffect(() => {
        const fetchTeam = async () => {
            if (teamcode) {
                const teamDataRes = await fetchTeamData(teamcode);
                setTeam(teamDataRes);
            }
        };
        fetchTeam();
    }, [teamcode]);

    const makeInvite = () => {
        const insertInvite = async () => {
            // ここに招待処理を書く
            if(invitedUser.trim() !== "") {
                const res = await insertInvitation(teamcode || "", invitedUser);
                if(res.status === 200) {
                    alert("招待しました");
                    setInvitedUser("");
                    setIsModalOpen(false);
                } else {
                    alert(res.data.message);
                }
            }
        }

        insertInvite();
    };


    return (
        <div className="team-home">
            <div>{team ? "" : "Loading..."}</div>
            <div id="team-icon icon">
                <TeamIcon imgUrl={team?.icon || defaultProfileData.icon} />
            </div>
            <div>
                <Link to={`/teams/${teamcode}/edit`}>編集</Link>
            </div>
            <div>
                <span className="team-name">{team?.name}</span>
                <span className="team-code">@{team?.code}</span>
            </div>
            <div>{team?.category}</div>
            <div id="follower">folower</div>
            <div className="description">{team?.description}</div>
            <div className="createAt">
                結成日 :
                <span className="create-date">
                    {team?.createAt instanceof Date
                        ? team?.createAt.toLocaleDateString("ja-JP", {
                                  year: "numeric",
                                  month: "2-digit",
                                  day: "2-digit",
                            })
                        : typeof team?.createAt === "string"
                        ? new Date(team?.createAt).toLocaleDateString("ja-JP")
                        : " "}
                </span>
            </div>
            <div id="refWeb">refWeb</div>
            <div className="members">
                <MemberList teamcode={teamcode || ""} />
                <Link 
                    className="icon make-invite"
                    to="#" 
                    onClick={() => setIsModalOpen(true)}>
                    メンバーを追加
                </Link>
            </div>
            <div className="announce-post">announcePost</div>
            <div className="ref-teams">refTeam</div>

            {/* 新規招待フォーム */}
            <Modal open={isModalOpen}>
                <div className="modal-content">
                    <div>
                        <input type="text"
                            value={invitedUser}
                            onChange={(e) => setInvitedUser(e.target.value)}
                            placeholder="招待するユーザーのID" />
                    </div>
                    <div>
                        <button onClick={makeInvite}>招待</button>
                        <button onClick={() => setIsModalOpen(false)}>閉じる</button>
                    </div>
                </div>
            </Modal>
        </div>
    );
};

export default TeamHome;
