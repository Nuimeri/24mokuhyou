import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchAttentionPostCode = async ( username : string ) => {
    try {
        const url : string = apiUrl + "/" + username + "/attention";
        const response = await axios.get<string>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching attention post code:',error);
        throw error;
    }
}