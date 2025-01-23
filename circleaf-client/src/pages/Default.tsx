import React from "react";
import { Route, Routes, useParams } from "react-router-dom";

//pages
import Header from "./Header";
import ErrorPage from "./ErrorPage";
import WrapperTeamHome from "./teams/TeamHome";
import ProfileEdit from "./profiles/ProfileEdit";
import WrapperTeamEdit from "./teams/TeamEdit";
import Footer from "./Footer";
import PostList from "./posts/PostList";
import PostDetail from "./posts/PostDetail";
import DmTop from "./dm/DmTop";
import DmRoom from "./dm/DmRoom";
import NoticeTop from "./notice/NoticeTop";
import NoticeDetail from "./notice/NoticeDetail";
import FindTop from "./find/FindTop";
import FindTeam from "./find/FindTeam";
import ProfileHome from "./profiles/ProfileHome";

// Defaultコンポーネントを関数コンポーネントに変換
const Default: React.FC = () => {
    return (
        <div className="wrapper">
            <Header />
            <hr style={{border:"1px solid black",width:"100%",margin:"0"}} ></hr>
            <main className="color-theme-lite">
                <Routes>
                    <Route path="/:username" element={<ProfileHome />} />
                    <Route path="/:username/edit" element={<ProfileEdit />} />
                    <Route path="/teams" element={<FindTeam isMine={true}/>} />
                    <Route path="/teams/new" element={<WrapperTeamEdit />} />
                    <Route path="/teams/:teamcode" element={<WrapperTeamHome />} />
                    <Route path="/teams/:teamcode/edit" element={<WrapperTeamEdit />} />
                    <Route path="/posts" element={<PostList />} />
                    <Route path="/posts/:username" element={<PostList />} />
                    <Route path="/post/:postCode" element={<WrapperPostDetail />} />
                    <Route path="/dm" element={<DmTop />} />
                    <Route path="/dm/:dmCode" element={<DmRoom />} />
                    <Route path="/notice" element={<NoticeTop />} />
                    <Route path="/notice/:noticeCode" element={<NoticeDetail />} />
                    <Route path="/find" element={<FindTop />} />
                    <Route path="/find/teams" element={<FindTeam isMine={false}/>} />
                    <Route path="/*" element={<ErrorPage />} />
                </Routes>
            </main>
            <Footer />
        </div>
    );
};

export default Default;

/* パラメータ受け取り用ラッパー */

// PostDetail
const WrapperPostDetail: React.FC = () => {
    const { postCode } = useParams<{ postCode: string }>(); // URLパラメータからpostCodeを取得

    // ProfileHomeコンポーネントにusernameをpropsとして渡す
    return <PostDetail postcode={postCode || ""} />;
};