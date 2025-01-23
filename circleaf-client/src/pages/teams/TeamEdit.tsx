import React, { useEffect, useRef, useState } from "react";
import { Team } from "../../Types";
import { defaultTeamData } from "../../constants/DefaultParameter";
import { Link, useNavigate, useParams } from "react-router-dom";
import TeamIcon from "../../component/TeamIcon";
import { insertTeamData } from "../../api/insertTeamData";
import { updateTeamData } from "../../api/updateTeamData";
import { fetchTeamData } from "../../api/fetchTeamData ";
import { fetchLoginStatus } from "../../api/fetchLoginStatus";

const TeamEdit: React.FC = () => {
  const { teamcode } = useParams<{ teamcode: string }>();
  const [teamData, setTeamData] = useState<Team>(defaultTeamData);
  const [iconImg, setIconImg] = useState<string>(import.meta.env.VITE_ICON_TEAM);
  const [username, setUsername] = useState<string>("");
  const fileInputRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();


  useEffect(() => {
    const fetchData = async () => {
      if (teamcode && teamcode !== " ") {
        const teamDataRes = await fetchTeamData(teamcode);
        
        setTeamData(teamDataRes);
        setIconImg(teamDataRes.icon);
        }
        const usernameRes = await fetchLoginStatus();
        setUsername(usernameRes || "");
    };

    fetchData();
  }, []);

  const handleChange = (key: string, value: string | number) => {
    setTeamData((prevData) => ({
      ...prevData,
      [key]: value,
    }));
  };

  const handleIconChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    if (e.currentTarget.files && e.currentTarget.files[0]) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setIconImg(reader.result as string);
      };
      reader.readAsDataURL(e.currentTarget.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      let teamUpdateRes = 1;
      if (teamcode && teamcode !== " ") {
        teamUpdateRes = await updateTeamData(teamcode, teamData);
      } else {
        teamUpdateRes = await insertTeamData(teamData);
      }

      if (teamUpdateRes === 1) {
        alert("登録しました");
        window.location.href = `/teams/${teamData.code}`;
      } else {
        alert("登録に失敗しました");
      }
    } catch (error) {
      alert("Error");
      console.error("Error updating profile: ", error);
    }
  };

  if(username == teamData.leader){
    navigate("/");

    return (
        <div>
            権限がないため
            <Link to={"/"}>トップページ</Link>
            へスキップします。
        </div>
    );
  }

  return (
    <div>
      {teamData ? "" : "Loading..."}
      <div>
        <Link to={"/teams/" + teamcode}>ホームに戻る</Link>
      </div>
      <form onSubmit={handleSubmit}>
        <div onClick={() => fileInputRef.current?.click()}>
          <TeamIcon imgUrl={iconImg} />
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
            <strong>チーム名</strong>
            <input
              type="text"
              value={teamData.name}
              onChange={(e) => handleChange("name", e.target.value)}
              className="team-edit-input"
            />
          </li>
          <li>
            <strong>チームID</strong>
            <input
              type="text"
              value={teamData.code.toString()}
              onChange={(e) => handleChange("code", e.target.value)}
              className="team-edit-input"
            />
          </li>
          <li>
            <strong>カテゴリー</strong>
            <input
              type="text"
              value={teamData.category.toString()}
              onChange={(e) => handleChange("category", e.target.value)}
              className="team-edit-input"
            />
          </li>
          <li>
            <strong>説明</strong>
            <textarea
              value={teamData.description.toString()}
              onChange={(e) => handleChange("description", e.target.value)}
              className="team-edit-input"
            />
          </li>
        </ul>
        <button type="submit">登録</button>
      </form>
    </div>
  );
};

export default TeamEdit;