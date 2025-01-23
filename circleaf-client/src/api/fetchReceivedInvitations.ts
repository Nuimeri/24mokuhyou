import axios from "axios";
import { Invitation } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchReceivedInvitations = async () => {
    try {
        const url : string = apiUrl + "/invitations/received";
        const response = await axios.get<Invitation[]>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching user data:',error);
        throw error;
    }
}

