import axios from "axios";
import { ProfileData } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const updateProfileData = async ( username : string , profileData : ProfileData) => {
    try {
        const url : string = apiUrl + "/" + username + "/edit";
        const response = await axios.post<number>(
            url 
            , profileData
        );
        return response.data;
    } catch (error) {
        console.error('Error posting user data:',error);
        throw error;
    }
}
