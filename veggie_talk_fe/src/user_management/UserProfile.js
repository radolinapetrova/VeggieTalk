import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./UserProfile.css";
import { useAuth } from "../auth/AuthProvider";
import { useNavigate } from "react-router-dom";

const UserProfile = () => {
    const [user, setUser] = useState({
        username: '',
        email: '',
        bio: ''
    });
    const { setAuth } = useAuth();
    const navigate = useNavigate();
    const [editing, setEditing] = useState(false);
    const [formData, setFormData] = useState(user);
    const [confirmDelete, setConfirmDelete] = useState(false);

    axios.defaults.headers.common = { 'Content-Type': 'application/json' };

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const accessToken = sessionStorage.getItem('accessToken');

                if (!accessToken) {
                    throw new Error('Auth token not found in session storage');
                }

                let decode = require('jwt-claims');
                const decodedToken = decode(accessToken);
                const email = decodedToken.email;

                const requestData = {
                    jwtToken: accessToken
                };

                const response = await axios.post('http://localhost:8083/api/account/username', requestData, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                setUser({
                    ...response.data,
                    email: email
                });

                setFormData({
                    ...response.data,
                    email: email
                });

            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        fetchUserData();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleUpdate = async () => {
        try {
            const response = await axios.put('http://localhost:8083/api/account', formData); // Adjust the URL as needed
            setUser(response.data);
            setEditing(false);
        } catch (error) {
            console.error('Error updating user data:', error);
        }
    };

    const handleDelete = async () => {
        try {
            const token = sessionStorage.getItem('accessToken'); // Retrieve auth token from session storage
            const username = user.username; // Assuming user.username is available

            // Define the deletion request bodies
            const deletionRequest = { jwtToken: token, username: username };

            await axios.delete('http://127.0.0.1:5000/delete_user', {
                data: deletionRequest,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            setAuth(false);
            navigate('/');

            console.log('User account deleted');
        } catch (error) {
            console.error('Error deleting user account:', error);
        }
    };

    const confirmDeleteHandler = () => {
        if (window.confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
            handleDelete();
        }
    };

    return (
        <div className="container">
            <h1 className="header">User Profile</h1>
            {editing ? (
                <div>
                    <div className="form-group">
                        <label>Username:</label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Bio:</label>
                        <textarea
                            name="bio"
                            value={formData.bio}
                            onChange={handleInputChange}
                        ></textarea>
                    </div>
                    <div className="button-group">
                        <button onClick={handleUpdate}>Save</button>
                        <button onClick={() => setEditing(false)}>Cancel</button>
                    </div>
                </div>
            ) : (
                <div>
                    <p><strong>Username:</strong> {user.username}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Bio:</strong> {user.bio}</p>
                    <div className="button-group">
                        <button onClick={() => setEditing(true)}>Edit</button>
                        <button onClick={confirmDeleteHandler}>Delete Account</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default UserProfile;
