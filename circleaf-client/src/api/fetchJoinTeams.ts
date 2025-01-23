import axios from "axios";
import { Team } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchJoinTeam = async ( username : string ) => {
    try {
        const url : string = apiUrl + "/" + username + "/teams";
        const response = await axios.get<Team[]>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching teams data:',error);
        throw error;
    }
}
