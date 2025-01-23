import axios from "axios";

const authorizationURL = "/isAuthenticated";
const apiUrl = import.meta.env.VITE_API_URL;

export const fetchAuth = async (username: string, password: string) => {
    try {
        const url : string = apiUrl + authorizationURL;
        const response = await axios.get(url
          , { auth : { username , password} } );
        return response;
    } catch (error) {
        console.error('Error fetching user data:',error);
        throw error;
    }
};
