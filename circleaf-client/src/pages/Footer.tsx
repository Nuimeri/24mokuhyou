import React from "react";
import { Link } from "react-router-dom";
import { ICON_PATH_FROM_SRC } from "../constants/Constants";

class Footer extends React.Component{
    render(): React.ReactNode {
        return(
            <footer className="">
                <div className="new-post-link ">
                    <span>+</span>
                </div>
                <div className="footer-body color-theme">
                    <div>
                        <Link to={"/posts"}>
                            <img 
                                src={"../" + ICON_PATH_FROM_SRC + "/svg/house_line.svg"} 
                                alt="投稿一覧"
                                loading="lazy"
                            />
                        </Link>
                    </div>
                    <div>
                        <Link to={"/find"}>
                            <img 
                                    src={"../" + ICON_PATH_FROM_SRC + "/svg/search_line.svg"} 
                                    alt="検索"
                                    loading="lazy"
                                />
                        </Link>
                    </div>
                    <div>
                        <Link to={"/teams"}>
                            <img 
                                    src={"../" + ICON_PATH_FROM_SRC + "/svg/family_line.svg"} 
                                    alt="所属チーム一覧"
                                    loading="lazy"
                                />
                        </Link>
                    </div>
                    <div>
                        <Link to={"/notice"}>
                            <img 
                                    src={"../" + ICON_PATH_FROM_SRC + "/svg/notification_line.svg"} 
                                    alt="通知"
                                    loading="lazy"
                                />
                        </Link>
                    </div>
                    <div>
                        <Link to={"/dm"}>
                            <img 
                                    src={"../" + ICON_PATH_FROM_SRC + "/svg/inbox_line.svg"} 
                                    alt="DM"
                                    loading="lazy"
                                />
                        </Link>
                    </div>
                </div>
            </footer>
        );
    }
}

export default Footer;