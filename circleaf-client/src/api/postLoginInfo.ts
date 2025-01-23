import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const postLoginInfo = async ( username: string, password: string) => {
    try {
        const url : string = apiUrl + "/login";
        const response = await axios.post(
            url
            , new URLSearchParams( {username, password})
            ,{
                headers: {
                  "Content-Type": "application/x-www-form-urlencoded", // サーバーが期待する形式
                },
                withCredentials: true// 認証情報をCookieとして含める
            }
        );

        return response;
    } catch (error) {
        console.error('Error fetching teams data:',error);
        throw error;
    }
}