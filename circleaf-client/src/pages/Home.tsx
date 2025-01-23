import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchLoginStatus } from "../api/fetchLoginStatus";

const Home : React.FC = () => {
    const [username, setUsername] = useState<string | null>(null);

    useEffect(() => {
        const checkLoginStatus = async () => {
            try {
                //   const response = await axios.get<string | null>('/isLogin', { withCredentials: true });
                const username = await fetchLoginStatus();
                
                setUsername(username); // ログイン中ならアカウント名、ログインしていなければNULL
            } catch (error) {
                console.error('Failed to check login status:', error);
                setUsername(null);
            }
        };
  
        checkLoginStatus();
    }, []);

    return(
        <div>
            <h1>
                Circleaf
            </h1>
            <h2>
                コンセプトコピー
            </h2>
            <div className="concept-text">
                説明文
            </div>
            <div className="account-navigation-link">
                { username? (
                    <div>
                        <Link to={"/" + username}> アカウントホーム</Link>
                    </div>
                ) : (
                    <li>
                        <ul>
                            <Link to="/Login">ログイン</Link>
                        </ul>
                        <ul>
                            <Link to="/SignUp">新規登録</Link>
                        </ul>
                    </li>
                )}
            </div>
        </div>
    );
}

export default Home;