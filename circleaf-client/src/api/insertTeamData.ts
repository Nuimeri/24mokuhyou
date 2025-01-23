import axios from "axios";
import { Team } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const insertTeamData = async (teamData : Team) => {
    try {
        const url : string = apiUrl + "/teams/create";
        const response = await axios.post<number>(url , teamData);
        return response.data;
    } catch (error) {
        console.error('Error inserting team data:',error);
        throw error;
    }
}
