import React, { useEffect } from "react";
import { useParams } from "react-router-dom";

const NoticeDetail : React.FC = () => {
    const { noticeCode } = useParams<{ noticeCode : string }>();

    useEffect(()=>{
        
    },[]);

    return (
        <div>
            通知詳細
            {noticeCode}
        </div>
    );
}

export default NoticeDetail;