import React, {useRef, useState} from "react";
import AuthenticationService from "../../services/AuthenticationService";
import {Navigate, useNavigate} from "react-router-dom";
import PopUpComponent from "../PopUpComponent/PopUpComponent";
import './LoginComponent.css';

const LoginComponent = () => {

    let navigate = useNavigate();

    const form = useRef();
    const checkBtn = useRef();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [popupMessage, setPopupMessage] = useState('');
    const [showPopup, setShowPopup] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };

    const saveOrUpdateProduct = (e) => {
        e.preventDefault();

        if (!username || !password) {
            setPopupMessage('Error username or  password mandatory.');
            setShowPopup(true);
            return;
        }

        AuthenticationService.login(username, password)
            .then(() => {
                navigate("/");
                window.location.reload();
            });
    };

    if (isLoggedIn) {
        return <Navigate to="/"/>;
    }

    return (
        <div className='add-product'>
            <div className="add-product-form">
                <h2>Login</h2>
                <form>
                    <div className="form-group">
                        <input type="text" id="username" name="username" placeholder="username" value={username}
                               onChange={(e) => setUsername(e.target.value)} required/>
                    </div>
                    <div className="form-group">
                        <input type="password" id="password" name="password" placeholder="password" value={password}
                               onChange={(e) => setPassword(e.target.value)} required/>
                    </div>
                    <div className='form-group-button'>
                        <button type="submit" onClick={(e) => saveOrUpdateProduct(e)}>
                            Login
                        </button>
                    </div>
                </form>
            </div>
            <PopUpComponent message={popupMessage} show={showPopup} onClose={() => setShowPopup(false)}/>
        </div>
    );
};

export default LoginComponent