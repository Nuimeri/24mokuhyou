import React from "react";
import "../assets/style/SiteLogo.css"
import { Link } from "react-router-dom";

const SiteLogo : React.FC = () => {
    return (
        <div className="site-logo">
            <Link to={"/"}>
                <img 
                    src="../public/circleaf-logo.png"
                    alt="site logo" 
                    loading="lazy" 
                    />
            </Link>
        </div>
    );
}

export default SiteLogo;