import axios from "axios";
import { Team } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchTeamData = async ( teamcode : string ) => {
    try {
        const url : string = apiUrl + "/teams/" + teamcode;
        const response = await axios.get<Team>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching user data:',error);
        throw error;
    }
}
