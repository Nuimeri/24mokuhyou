import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchLoginStatus = async () => {
    try {
        const url : string = apiUrl + "/isLogin";
        const response = await axios.get<string | null>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching login status:',error);
        throw error;
    }
}
