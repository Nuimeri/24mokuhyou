import axios from "axios";
import { ProfileData } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchProfileData = async ( username : string ) => {
    try {
        const url : string = apiUrl + "/" + username;
        const response = await axios.get<ProfileData>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching user data:',error);
        throw error;
    }
}
