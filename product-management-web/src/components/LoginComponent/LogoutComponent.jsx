import React, {useEffect} from "react";
import AuthenticationService from "../../services/AuthenticationService";
import {useNavigate} from "react-router-dom";

const LogoutComponent = () => {

    let navigate = useNavigate();

    useEffect(() => {
        AuthenticationService.logout();
        navigate("/login");
        window.location.reload();
    }, []);

    return (
        <div>
        </div>
    );
};

export default LogoutComponent