import React from "react";
import { Link } from "react-router-dom";

export default function Homepage() {
    // Check if user has just created an account
    const hasCreatedAccount = localStorage.getItem("createdAccount");

    return (
        <div>
            <p>Welcome to Veggie Talk, your online hub for all things plant-based!
                Whether you're a seasoned vegan, a curious flexitarian,
                or simply seeking healthier lifestyle choices,
                Veggie Talk offers a vibrant community and a wealth of resources to support your journey.
                From delicious recipes and nutrition tips to sustainable living insights and inspiring stories,
                join us as we celebrate the power of plant-based living together.</p>
            {/* Conditional rendering based on whether the user has just created an account */}
            {!hasCreatedAccount && <Link to={"/login"} className="title">Create an account</Link>}
        </div>
    )
}
