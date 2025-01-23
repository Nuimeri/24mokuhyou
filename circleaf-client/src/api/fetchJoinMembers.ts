import axios from "axios";
import { ProfileData } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchJoinMember = async ( teamcode : string ) => {
    try {
        const url : string = apiUrl + "/teams/" + teamcode + "/members";
        const response = await axios.get<ProfileData[]>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching members data:',error);
        throw error;
    }
}
