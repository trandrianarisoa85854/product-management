// Popup.js
import React, {useEffect, useState} from 'react';
import './PopUpComponent.css';

const Popup = ({ message, show, onClose }) => {
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        setIsVisible(show);
    }, [show]);

    useEffect(() => {
        if (isVisible) {
            const timeout = setTimeout(() => {
                onClose();
            }, 3000);

            return () => {
                clearTimeout(timeout);
            };
        }
    }, [isVisible, onClose]);

    return (
        <div className={`popup-container ${isVisible ? 'popup-show' : ''}`}>
            {message}
        </div>
    );
};

export default Popup;
