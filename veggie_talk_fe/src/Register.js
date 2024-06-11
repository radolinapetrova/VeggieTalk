import React, { useState } from "react";
import axios from "axios";
import "./Account.css";

export default function Register() {
    const [userData, setUserData] = useState({
        username: "",
        password: "",
        email: "",
    });

    const [agreeGDPR, setAgreeGDPR] = useState(false);
    const [msg, setMsg] = useState("");

    const HandleSubmit = async (e) => {
        e.preventDefault();
        console.log("Form submitted");
        if (!agreeGDPR) {
            setMsg("You must agree to the terms of GDPR to register.");
            return;
        }
        try {
            console.log("Sending user data:", userData);
            let response = await axios.post("http://127.0.0.1:5000/create_user", userData);
            console.log("Response received:", response);
            setMsg("Registration successful!");
        } catch (err) {
            console.log("Error during registration:", err);
            setMsg("Registration failed. Please try again later.");
        }
    };

    return (
        <div>
            <form className="register" onSubmit={HandleSubmit}>
                <div className="input">
                    <label>Email </label>
                    <input
                        type="text"
                        value={userData.email}
                        name="email"
                        onChange={(e) =>
                            setUserData((prevState) => ({
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
                        value={userData.password}
                        name="password"
                        onChange={(e) =>
                            setUserData((prevState) => ({
                                ...prevState,
                                password: e.target.value,
                            }))
                        }
                        required
                    />
                </div>
                <div className="input">
                    <label>Username</label>
                    <input
                        type="text"
                        value={userData.username}
                        name="username"
                        onChange={(e) =>
                            setUserData((prevState) => ({
                                ...prevState,
                                username: e.target.value,
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
                            onChange={(e) => setAgreeGDPR(e.target.checked)}
                        />
                        I agree to the terms of GDPR
                    </label>
                </div>
                <button className="button" name="registerButton" type="submit">
                    Register
                </button>
                <p className="message">{msg}</p>
            </form>
        </div>
    );
}
