import axios from "axios";
import { SignupData } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const insertAccount = async (signup : SignupData) => {
    try {
        const url : string = apiUrl + "/signup";
        const response = await axios.post<string>(url , signup);
        return response.data;
    } catch (error) {
        console.error('Error inserting account data:',error);
        throw error;
    }
}
