import React, { useState } from "react";
import axios from "axios";
import "../Account.css";
import { useNavigate } from 'react-router-dom';
import {useAuth} from "./AuthProvider";

export default function Account() {

    const {setAuth, setClaims} = useAuth();
    let decode = require('jwt-claims');

    const navigate = useNavigate();

    const [isRegistering, setIsRegistering] = useState(false);
    const [userData, setUserData] = useState({
        username: "",
        password: "",
        email: "",
        bio: "You can write something about yourself here!"
    });

    const [agreeGDPR, setAgreeGDPR] = useState(false);
    const [msg, setMsg] = useState("");

    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        if (!agreeGDPR) {
            setMsg("You must agree to the terms of GDPR to register.");
            return;
        }
        try {
            await axios.post("http://127.0.0.1:5000/create_user", userData);
            await axios.post("http://localhost:8083/api/account", userData)
            setMsg("Registration successful! Please log in now.");
            setIsRegistering(false);
        } catch (err) {
            setMsg("Registration failed. Please try again later.");
        }
    };

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(userData)
            let res = await axios.post("http://127.0.0.1:5000/login", userData);
            setMsg("Login successful!");
            sessionStorage.setItem('accessToken', res.data["access_token"]);
            sessionStorage.setItem('refreshToken', res.data["refresh_token"]);
            const token = res.data["access_token"];
            setClaims(decode(token));
            setAuth(true);
            navigate('/');

        } catch (err) {
            setMsg("Login failed. Please check your credentials and try again.");
        }
    };

    return (
        <div className="account-container">
            {isRegistering ? (
                <form className="register" onSubmit={handleRegisterSubmit}>
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
                    <p className="toggle-message">
                        Already have an account?{" "}
                        <span onClick={() => setIsRegistering(false)}>Log in here</span>
                    </p>
                </form>
            ) : (
                <form className="login" onSubmit={handleLoginSubmit}>
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
                        <label>Password</label>
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
                    <button className="button" name="loginButton" type="submit">
                        Log In
                    </button>
                    <p className="message">{msg}</p>
                    <p className="toggle-message">
                        Don't have an account?{" "}
                        <span onClick={() => setIsRegistering(true)}>Register here</span>
                    </p>
                </form>
            )}
        </div>
    );
}
