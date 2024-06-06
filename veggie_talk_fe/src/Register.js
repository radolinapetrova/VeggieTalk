import React, { useState } from "react";
import axios from "axios";
import "./Account.css";

export default function Register() {
    const [data, setData] = useState({
        email: "",
        userId: "",
        bio: "",
        id: ""
    });
    const [agreeGDPR, setAgreeGDPR] = useState(false);
    const [msg, setMsg] = useState("");
    const [showModal, setShowModal] = useState(false);

    const HandleSubmit = async (e) => {
        e.preventDefault();
        if (!agreeGDPR) {
            setMsg("You must agree to the terms of GDPR to register.");
            return;
        }
        try {
            // Simulate registration success
            setMsg("");
            setShowModal(true);
            // Save to local storage
            localStorage.setItem("createdAccount", true); // Set a flag indicating the user has created an account
            // Optionally, you can set a timeout to close the modal after a few seconds
            setTimeout(() => {
                setShowModal(false);
                // Redirect to homepage
                window.location.href = "http://localhost:3000";
            }, 3000); // Adjust the timeout as needed
        } catch (err) {
            // Handle registration error
            setMsg("Registration failed. Please try again later.");
        }
    };

    return (
        <div>
            <form className="register">
                <div className="input">
                    <label>Email </label>
                    <input
                        type="text"
                        value={data.email}
                        name="email"
                        onChange={(e) =>
                            setData((prevState) => ({
                                ...prevState,
                                email: e.target.value,
                            }))
                        }
                        required
                    />
                </div>
                <div className="input">
                    <label>Password </label>
                    <input
                        type="password"
                        value={data.password}
                        name="password"
                        onChange={(e) =>
                            setData((prevState) => ({
                                ...prevState,
                                password: e.target.value,
                            }))
                        }
                        required
                    />
                </div>
                <div className="input">
                    <label>
                        <input
                            type="checkbox"
                            checked={agreeGDPR}
                            onChange={(e) =>
                                setAgreeGDPR(e.target.checked)
                            }
                        />
                        I agree to the terms of GDPR
                    </label>
                </div>
                <button className="button" name="registerButton" onClick={HandleSubmit}>
                    Register
                </button>
                <p className="message">{msg}</p>
            </form>
            {/* Success modal */}
            {showModal && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={() => setShowModal(false)}>&times;</span>
                        <p>You have successfully created a new account.</p>
                    </div>
                </div>
            )}
        </div>
    );
}
