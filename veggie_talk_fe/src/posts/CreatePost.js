import React, { useState } from "react";
import "./PostForm.css";

function PostForm() {
    const [formData, setFormData] = useState({
        description: "",
        isRecipe: false,
        category: "",
        difficulty: "",
        ingredients: [""],
        images: [],
        videos: []
    });

    const handleChange = (e, index) => {
        const { name, value, type, checked } = e.target;
        if (name === "ingredients") {
            const updatedIngredients = [...formData.ingredients];
            updatedIngredients[index] = value;
            setFormData((prevData) => ({
                ...prevData,
                ingredients: updatedIngredients
            }));
        } else if (type === "file") {
            setFormData((prevData) => ({
                ...prevData,
                [name]: [...prevData[name], ...e.target.files]
            }));
        } else {
            setFormData((prevData) => ({
                ...prevData,
                [name]: type === "checkbox" ? checked : value === "true"
            }));
        }
    };

    const handleAddIngredient = () => {
        setFormData((prevData) => ({
            ...prevData,
            ingredients: [...prevData.ingredients, ""]
        }));
    };

    const handleRemoveIngredient = (index) => {
        const updatedIngredients = [...formData.ingredients];
        updatedIngredients.splice(index, 1);
        setFormData((prevData) => ({
            ...prevData,
            ingredients: updatedIngredients
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission
        console.log(formData);
    };

    return (
        <form className="post-form" onSubmit={handleSubmit}>
            <div className="form-group">
                <label>Description:</label>
                <textarea
                    className="form-control"
                    name="description"
                    value={formData.description}
                    onChange={handleChange}
                    required
                />
            </div>
            <div className="form-group">
                <label>Post Type:</label>
                <label>
                    <input
                        type="radio"
                        name="isRecipe"
                        value="false"
                        checked={!formData.isRecipe}
                        onChange={handleChange}
                    />
                    Regular Post
                </label>
                <label>
                    <input
                        type="radio"
                        name="isRecipe"
                        value="true"
                        checked={formData.isRecipe}
                        onChange={handleChange}
                    />
                    Recipe
                </label>
            </div>
            {formData.isRecipe && (
                <div className="recipe-fields">
                    <div className="form-group">
                        <label>Category:</label>
                        <select
                            className="form-control"
                            name="category"
                            value={formData.category}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select category</option>
                            <option value="BREAKFAST">Breakfast</option>
                            <option value="LUNCH">Lunch</option>
                            <option value="DINNER">Dinner</option>
                            <option value="DESSERT">Dessert</option>
                            <option value="SNACK">Snack</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Difficulty:</label>
                        <select
                            className="form-control"
                            name="difficulty"
                            value={formData.difficulty}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select difficulty</option>
                            <option value="EASY">Easy</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="HARD">Hard</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Ingredients:</label>
                        {formData.ingredients.map((ingredient, index) => (
                            <div key={index} className="ingredient-input">
                                <input
                                    type="text"
                                    className="form-control"
                                    name="ingredients"
                                    value={ingredient}
                                    onChange={(e) => handleChange(e, index)}
                                    required
                                />
                                <button
                                    type="button"
                                    onClick={() => handleRemoveIngredient(index)}
                                >
                                    Remove
                                </button>
                            </div>
                        ))}
                        <button type="button" onClick={handleAddIngredient}>
                            Add Ingredient
                        </button>
                    </div>
                </div>
            )}
            <div className="form-group">
                <label>Images:</label>
                <input
                    type="file"
                    name="images"
                    accept="image/*"
                    onChange={handleChange}
                    multiple
                />
            </div>
            <div className="form-group">
                <label>Videos:</label>
                <input
                    type="file"
                    name="videos"
                    accept="video/*"
                    onChange={handleChange}
                    multiple
                />
            </div>
            <button className="btn btn-primary" type="submit">
                Submit
            </button>
        </form>
    );
}

export default PostForm;
