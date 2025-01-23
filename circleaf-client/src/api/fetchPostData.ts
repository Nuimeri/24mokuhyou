import axios from "axios";
import { Post } from "../Types";

const apiUrl = import.meta.env.VITE_API_URL;

export const fetchPostData = async ( postcode : string ) => {
    try {
        const url : string = apiUrl + "/posts/" + postcode;
        const response = await axios.get<Post>(url);
        return response.data;
    } catch (error) {
        console.error('Error fetching user data:',error);
        throw error;
    }
}

