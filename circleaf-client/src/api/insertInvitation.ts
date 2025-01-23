import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const insertInvitation = async (teamCode : string, username : string) => {
    try {
        const url : string = apiUrl + "/invitations/" + teamCode + "/ticket";
        const response = await axios.post<{message : string}>(url , {username});
        return response;
    } catch (error) {
        console.error('Error inserting account data:',error);
        throw error;
    }
}
