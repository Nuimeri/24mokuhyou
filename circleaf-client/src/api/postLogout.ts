import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const postLogout = async () => {
    try {
        const url : string = apiUrl + "/logout";
        const response = await axios.post(url,{});
        return response;
    } catch (error) {
        console.error('Error tty log out:',error);
        throw error;
    }
};
