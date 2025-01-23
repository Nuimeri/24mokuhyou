import axios from "axios";

const apiUrl = import.meta.env.VITE_API_URL;

export const updateRejectInvitation = async ( ticketCode : string) => {
    try {
        const url : string = apiUrl + "/invitations/reject" ;
        const response = await axios.post<number>(url, {ticketCode}, {withCredentials: true});
        return response.data;
    } catch (error) {
        console.error('Error rejecting invitation: ',error);
        throw error;
    }
}
