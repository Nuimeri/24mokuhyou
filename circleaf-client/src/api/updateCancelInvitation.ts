import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const updateCancelInvitation = async ( ticketCode : string) => {
    try {
        const url : string = apiUrl + "/invitations/cancel" ;
        const response = await axios.post<number>(url, {ticketCode}, {withCredentials: true});
        return response.data;
    } catch (error) {
        console.error('Error accepting invitation:',error);
        throw error;
    }
}
