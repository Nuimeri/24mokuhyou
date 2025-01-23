import axios from "axios";
import { Team } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const updateTeamData = async ( teamcode : string , teamData : Team) => {
    try {
        const url : string = apiUrl + "/teams/" + teamcode + "/edit";
        const response = await axios.post<number>(url , teamData);
        return response.data;
    } catch (error) {
        console.error('Error posting team data:',error);
        throw error;
    }
}
